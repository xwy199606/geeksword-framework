package org.geeksword.xwy.model;

import javax.persistence.*;

/*
 * @Author :xwy
 * @poem：悟来时见江海古，苍崖行遍谒玄门。向道偶题人间世,一笛一剑一昆仑。
 * @Date :Created in 2019/3/7  12:25 PM
 * @Description:测试jpa自动生成数据库表
 */
@Entity
@Table(name = "bear_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private String address;
    private Double consume;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }
}
