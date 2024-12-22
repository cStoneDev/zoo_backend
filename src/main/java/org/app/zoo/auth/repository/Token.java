package org.app.zoo.auth.repository;

import org.app.zoo.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "tokens")
public class Token {

    private enum TokenType {
        BEARER
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // or GenerationType.SEQUENCE
    private Long id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private static TokenType tokenType = TokenType.BEARER;
    
        private boolean revoked;
    
        private boolean expired;
    
        public Token(User user,String token, boolean revoked, boolean expired){
            setUser(user);
            setToken(token);
            setRevoked(revoked);
            setExpired(expired);
        }
    
        public Token(){}
    
        public Token(User user, String token){
          setUser(user);
          setToken(token);
        }
    
        public Token(Long id, User user, String token, boolean revoked, boolean expired) {
          setId(id);
          setUser(user);
          setToken(token);
          setRevoked(revoked);
          setExpired(expired);
        }
    
        public Long getId() {
            return id;
        }
    
        public void setId(Long id) {
            this.id = id;
        }
    
        public String getToken() {
            return token;
        }
    
        public void setToken(String token) {
            this.token = token;
        }
    
        public static TokenType getTokenType() {
            return tokenType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
