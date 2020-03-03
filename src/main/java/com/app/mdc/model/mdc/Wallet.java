package com.app.mdc.model.mdc;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-02-05
 */
@TableName("mdc_wallet")
public class Wallet extends Model<Wallet> {
    private static final long serialVersionUID = 1L;
    /**
     * 钱包id
     */
    @TableId(value = "wallet_id", type = IdType.AUTO)
    private Integer walletId;
    /**
     * 钱包密码
     */
    private String password;
    /**
     * 钱包地址
     */
    private String address;
    /**
     * 钱包物理地址
     */
    private String walletPath;
    /**
     * 公共密钥
     */
    private String publicKey;
    /**
     * 私有密钥
     */
    private String privateKey;
    /**
     * 余额
     */
    private BigDecimal ethBlance;
    private BigDecimal ustdBlance;

    private BigDecimal mdcBlance;
    /**
     * 用户id
     */
    private Integer userId;

    private Date createTime;


    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWalletPath() {
        return walletPath;
    }

    public void setWalletPath(String walletPath) {
        this.walletPath = walletPath;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public BigDecimal getUstdBlance() {
        return ustdBlance;
    }

    public void setUstdBlance(BigDecimal ustdBlance) {
        this.ustdBlance = ustdBlance;
    }

    public BigDecimal getMdcBlance() {
        return mdcBlance;
    }

    public void setMdcBlance(BigDecimal mdcBlance) {
        this.mdcBlance = mdcBlance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getEthBlance() {
        return ethBlance;
    }

    public void setEthBlance(BigDecimal ethBlance) {
        this.ethBlance = ethBlance;
    }

    @Override
    protected Serializable pkVal() {
        return this.walletId;
    }

    @Override
    public String toString() {
        return "Wallet{" +
        ", walletId=" + walletId +
        ", password=" + password +
        ", address=" + address +
        ", walletPath=" + walletPath +
        ", publicKey=" + publicKey +
        ", privateKey=" + privateKey +
        ", userId=" + userId +
        "}";
    }
}
