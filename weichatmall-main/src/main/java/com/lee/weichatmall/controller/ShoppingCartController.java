package com.lee.weichatmall.controller;

import com.lee.weichatmall.domain.responesesEntity.PageResponse;
import com.lee.weichatmall.domain.responesesEntity.Response;
import com.lee.weichatmall.domain.shoppingCart.GoodsToShoppingCartList;
import com.lee.weichatmall.domain.shoppingCart.ShoppingCartGoodsWithShop;
import com.lee.weichatmall.service.ShoppingCartService;
import com.lee.weichatmall.service.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    // @formatter:off

    /**
     * @api {get} /shoppingCart 获取当前用户名下的所有购物车物品
     * @apiName GetShoppingCart
     * @apiGroup 购物车
     * @apiHeader {String} Accept application/json
     * @apiParam {Number} pageNum 页数，从1开始
     * @apiParam {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     * @apiSuccess {ShoppingCart} data 购物车物品列表，按照店铺分组
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "pageNum": 1,
     * "pageSize": 10,
     * "totalPage": 5,
     * "data": [
     * {
     * "shop": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * "goods": [
     * {
     * "id": 12345,
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "address": "XXX",
     * "price": 500,
     * "number": 10,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * {
     * ...
     * }
     * ]
     * }
     * ]
     * }
     * @apiError 401 Unauthorized 若用户未登录
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     * @param pageNum
     * @param pageSize
     * @param response
     * @return 返回所有购物车列表，以shop分页
     */
    // @formatter:on
    @GetMapping("shoppingCart")
    public PageResponse<ShoppingCartGoodsWithShop> getShoppingCart(@RequestParam int pageNum,
                                                                   @RequestParam int pageSize,
                                                                   HttpServletResponse response) {
        try {
            return PageResponse.newInstance(pageNum, pageSize,
                    shoppingCartService.getUserShoppingCartByShopPage(pageNum, pageSize),
                    shoppingCartService.getUserShoppingCartShopCount());
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return PageResponse.newInstance(0, 0, null, 0);
        }
    }

    // @formatter:off

    /**
     * @api {post} /shoppingCart 加购物车
     * @apiName AddShoppingCart
     * @apiGroup 购物车
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample {json} Request-Example:
     * {
     * "goods": [
     * {
     * "id": 12345,
     * "number": 10,
     * },
     * {
     * ...
     * }
     * }
     * @apiSuccess {ShoppingCart} data 更新后的该店铺物品列表
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "data": {
     * "shop": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * "goods": [
     * {
     * "id": 12345,
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "address": "XXX",
     * "price": 500,
     * "number": 10,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * {
     * ...
     * }
     * ]
     * }
     * }
     * }
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 404 Not Found 若商品未找到
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     * @param goodsToShoppingCart
     * @param response
     * @return 返回当前用户添加此shop的商品
     */
    // @formatter:on
    @PostMapping("shoppingCart")
    public Response<ShoppingCartGoodsWithShop> addShoppingCart(@RequestBody GoodsToShoppingCartList goodsToShoppingCart, HttpServletResponse response) {
        try {
            return Response.of(shoppingCartService.addShoppingCart(goodsToShoppingCart));
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }

    // @formatter:off

    /**
     * @api {delete} /shoppingCart/:goodsId 删除当前用户购物车中指定的商品
     * @apiName DeleteShoppingCart
     * @apiGroup 购物车
     * @apiHeader {String} Accept application/json
     * @apiParam {Number} goodsId 要删除的商品ID
     * @apiSuccess {ShoppingCart} data 更新后的该店铺物品列表
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "data": {
     * "shop": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * "goods": [
     * {
     * "id": 12345,
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "address": "XXX",
     * "price": 500,
     * "number": 10,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * {
     * ...
     * }
     * ]
     * }
     * }
     * }
     * @apiError 401 Unauthorized 若用户未登录
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     *
     * @param goodsId
     * @param response
     * @return 返回删除后此店铺在购物车的商品
     */
    // @formatter:on
    @DeleteMapping("shoppingCart/{goodsId}")
    public Response<ShoppingCartGoodsWithShop> deleteShoppingCart(@PathVariable("goodsId") long goodsId, HttpServletResponse response) {
        try {
            return Response.of(shoppingCartService.deleteShoppingCartGoodsById(goodsId));
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }
}
