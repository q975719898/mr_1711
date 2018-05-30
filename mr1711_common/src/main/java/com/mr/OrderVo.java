package com.mr;

import com.mr.model.TbOrder;
import com.mr.model.TbOrderItem;
import com.mr.model.TbOrderShipping;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by czl on 2018/5/24.
 */
@Data
public class OrderVo extends TbOrder implements Serializable {

    private TbOrderShipping tbOrderShipping;

    private List<TbOrderItem> tbOrderItems;
}
