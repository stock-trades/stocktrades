package io.stocktrades.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ToString
public class OrderDto {

    @JsonProperty("exchange")
    public String exchange;

    @JsonProperty("tradingsymbol")
    public String tradingsymbol;

    @JsonProperty("transactionType")
    public String transactionType;

    @JsonProperty("quantity")
    public Integer quantity;

    @JsonProperty("price")
    public Double price;

    @JsonProperty("product")
    public String product;

    @JsonProperty("orderType")
    public String orderType;

    @JsonProperty("validity")
    public String validity;

    @JsonProperty("disclosedQuantity")
    public Integer disclosedQuantity;

    @JsonProperty("triggerPrice")
    public Double triggerPrice;

    @JsonProperty("squareoff")
    public Double squareoff;

    @JsonProperty("stoploss")
    public Double stoploss;

    @JsonProperty("trailingStoploss")
    public Double trailingStoploss;

    @JsonProperty("tag")
    public String tag;

    @JsonProperty("parentOrderId")
    public String parentOrderId;

}
