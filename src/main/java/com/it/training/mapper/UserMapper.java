package com.it.training.mapper;

import java.util.List;
import com.it.training.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO users (email, password, name) VALUES (#{user.email}, #{user.password}, #{user.name})")
    void insert(@Param("user") User user);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User getByEmail(@Param("email") String email);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(@Param("id") long id);

    @Select("SELECT * FROM users LIMIT #{limit} OFFSET #{offset}")
    List<User> getAll(@Param("limit") int limit,@Param("offset") int offset);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void deleteById(@Param("id") long id);

    @Update("UPDATE users SET name = #{user.name},email= #{user.email},password= #{user.password} WHERE id = #{user.id}")
    void update(@Param("user") User user);
}
