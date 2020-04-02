package com.lee.weichatmall.dao.mapper;

import com.lee.weichatmall.domain.Order;
import com.lee.weichatmall.domain.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    long countByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int deleteByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    List<Order> selectByExample(OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    Order selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ORDER
     *
     * @mbg.generated Thu Apr 02 10:58:48 CST 2020
     */
    int updateByPrimaryKey(Order record);
}