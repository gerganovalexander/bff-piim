package com.tinqin.academy.bff.business.operations.authentication;

import com.tinqin.academy.bff.domain.ClientInterpreter;
import com.tinqin.academy.piim.api.errors.token.FindAllValidTokenByUserError;
import com.tinqin.academy.piim.api.token.create.CreateTokenInput;
import com.tinqin.academy.piim.api.token.findallvalidtokenbyuser.FindAllValidTokenByUserInput;
import com.tinqin.academy.piim.api.token.findallvalidtokenbyuser.FindAllValidTokenByUserResult;
import com.tinqin.academy.piim.api.token.revoke.RevokeTokenInput;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final ClientInterpreter clientInterpreter;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void saveUserToken(UserDetails user, String jwtToken) {
        clientInterpreter.createToken(CreateTokenInput.builder()
                .token(jwtToken)
                .tokenType("BEARER")
                .email(user.getUsername())
                .build());
    }

    public void revokeAllUserTokens(UserDetails user) {
        Either<FindAllValidTokenByUserError, FindAllValidTokenByUserResult> validUserTokens =
                clientInterpreter.findAllValidTokenByUser(FindAllValidTokenByUserInput.builder()
                        .email(user.getUsername())
                        .build());
        if (validUserTokens.isRight()) {
            validUserTokens.get().getTokens().parallelStream()
                    .forEach(tokenOutput -> clientInterpreter.revokeToken(RevokeTokenInput.builder()
                            .token(tokenOutput.getToken())
                            .build()));
        }
    }
}
