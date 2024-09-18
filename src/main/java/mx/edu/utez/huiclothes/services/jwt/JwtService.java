package mx.edu.utez.huiclothes.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "Q29uIHVuIHZhc2l0byBlbiBsYSBtYW5vLCBqYSBlbCBjb3JwbvJlIHNlbnRpbyBjYW5zYWRvIEZ1bWFuZG8gd29nb3MgcmVsYXphZG8gbyBsYSBmZXN0YSBub8Otc3Bhcg==";

    public String getToken(UserDetails userBean) {
        return getToken(new HashMap<>(),userBean);
    }


    private String getToken(Map<String,Object> extraClaims, UserDetails userBean) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userBean.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact()
                ;
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }


    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }


    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
