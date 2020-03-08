package com.app.mdc.model.mdc;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@TableName("vas_wallet")
@AllArgsConstructor
@Data
@Builder
public class VasWallet{
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String pubkey;
    private String privkey;
    private Double balance;
    private String address;
    private String path;

    public VasWallet() {
    }
}
