package com.lucas.xunta.common.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -3301605591108950415L;

    private static final String PRE_FIX_TOKEN = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Integer expiration;

    @Value("${jwt.expiration-status}")
    private boolean expirationStatus;

    @Value("${jwt.token}")
    private String tokenHeader;

    private Clock clock = DefaultClock.INSTANCE;

    public String generateToken(String userInfo) {
        Map<String, Object> claims = new HashMap<>();
        return PRE_FIX_TOKEN + doGenerateToken(claims, userInfo);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = DateUtils.addSeconds(createdDate, expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
        );
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断 jwt 是否过期
     *
     * @param token
     * @return true:过期 false:没过期
     */
    public Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(clock.now());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 解析 jwt token
     *
     * @param token 需要解析的json
     * @return
     */
    public Jws<Claims> parserAuthenticateToken(String token) {
        try {
            final Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return claimsJws;
        } catch (ExpiredJwtException e) {
            return new DefaultJws<>(null, e.getClaims(), "");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | IncorrectClaimException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 判断 jwt 是否过期
     *
     * @param jws
     * @return true:过期 false:没过期
     */
    public static boolean isJwtExpired(Jws<Claims> jws) {
        return jws.getBody().getExpiration().before(new Date());
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(PRE_FIX_TOKEN)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public long getExpirationTime() {
        return expiration;
    }
}