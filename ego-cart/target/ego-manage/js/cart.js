var TTCart = {
	load : function(){ // 加载购物车数据
		
	},
	itemNumChange : function(){
		$(".increment").click(function(){//＋
			var _thisInput = $(this).siblings("input");
			_thisInput.val(eval(_thisInput.val()) + 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) == 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
		$(".quantity-form .quantity-text").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
		});
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	}
};

$(function(){
	TTCart.load();
	TTCart.itemNumChange();

	//删除购物车商品
	$(".cart-remove").click(function () {
		var href = $(this).attr("href");
		var $a = $(this);
		$.ajax({
			url:href,
			success:function (data) {
				if(data.status == 200){
                    $a.parent().parent().parent().remove();
                    TTCart.refreshTotalPrice();
				}
            }
		});
		return false;

    })

	//“复选框”选中计算总价
	$(".checkbox").click(function () {
        var total = 0;
        $(".quantity-form .quantity-text").each(function(i,e){
            var _this = $(e);
            var isChecked =_this.parent().parent().siblings(".p-checkbox").children().eq(0).is(":checked");
            //alert(isChecked);
            if (isChecked) {
                total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
            }
        });
        $(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
            prefix: '￥',
            thousandsSeparator: ',',
            centsLimit: 2
        });
    })

	//"去结算"绑定单击事件
	$("#toSettlement").click(function () {
		var href = $(this).attr("href");
		var param =href+"?";
		$(".checkbox:checked").each(function (i,e) {
            var _this = $(e);
            param += "ids="+_this.val();
            if(i < $(".checkbox:checked").length-1){
				param += "&";
			}
        });
		window.location.href=param;
		return false;
    })
});