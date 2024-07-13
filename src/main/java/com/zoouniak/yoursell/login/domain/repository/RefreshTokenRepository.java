package com.zoouniak.yoursell.login.domain.repository;

import com.zoouniak.yoursell.login.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
