package com.project.cartel.repository;

import com.project.cartel.entity.Machines;
import com.project.cartel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u WHERE u.self_referral_code = :refralcode")
    public User getUserByRefralCode(@Param("refralcode") String refralcode);

    @Query(value = "SELECT u FROM User u WHERE u.phone_number = :number")
    public User getUserByNumber(@Param("number")Long number);

//    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.machines WHERE u.user_id = :user_id")
//    public List<User> getSingleUserMachines(@Param("user_id") int user_id);

//    @Query(value = "SELECT m FROM Machines m JOIN m.user u WHERE u.user_id = :user_id")
//    public List<Machines> getUserAllMachines(@Param("user_id") int user_id);

    @Modifying
    @Query(value = "UPDATE User u SET u.total_interest_earned = :totalInterest, u.available_to_withdraw = :availableToWithdraw WHERE u.user_id =  :userId")
    public void updateInterestAndWithdrawal(@Param("userId")int userId,
                                            @Param("totalInterest") int totalInterest,
                                            @Param("availableToWithdraw") int availableToWithdraw);
}
