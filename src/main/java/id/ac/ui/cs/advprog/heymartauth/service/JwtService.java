package id.ac.ui.cs.advprog.heymartauth.service;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = null;

    public String extractUsername(String token) {
        return null;
    }

    public String extractId(String token) {
        return null;
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    public boolean isTokenExpired(String token) {
        return false;
    }

    private Date extractExpiration(String token) {
        return null;
    }

    public String generateToken(UserDetails userDetails) {
        return null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return null;
    }

    private Claims extractAllClaims(String token){
        return null;
    }

    private Key getSignInKey(){
        return null;
    }
}
