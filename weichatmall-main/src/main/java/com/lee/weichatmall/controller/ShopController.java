package com.lee.weichatmall.controller;

import com.lee.weichatmall.domain.Shop;
import com.lee.weichatmall.domain.responesesEntity.PageResponse;
import com.lee.weichatmall.domain.responesesEntity.Response;
import com.lee.weichatmall.service.ShopService;
import com.lee.weichatmall.service.UserContext;
import com.lee.weichatmall.service.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ShopController {
    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }
    // @formatter:off

    /**
     * @api {get} /shop 获取当前用户名下的所有店铺
     * @apiName GetShop
     * @apiGroup 店铺
     * @apiHeader {String} Accept application/json
     * @apiParam {Number} pageNum 页数，从1开始
     * @apiParam {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     * @apiSuccess {Shop} data 店铺列表
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "pageNum": 1,
     * "pageSize": 10,
     * "totalPage": 5,
     * "data": [
     * {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * {
     * ...
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
     * @return 返回店铺列表
     */
    // @formatter:on
    @GetMapping("shop")
    public PageResponse<Shop> getShop(@RequestParam int pageNum, @RequestParam int pageSize) {
        return shopService.getShopByPage(pageNum, pageSize);
    }

    // @formatter:off

    /**
     * @api {post} /shop 创建店铺
     * @apiName CreateShop
     * @apiGroup 店铺
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample {json} Request-Example:
     * {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 201 Created
     * {
     * "data": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * }
     * }
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     * @param shop
     * @param response
     * @return 返回创建的shop
     */
    // @formatter:on
    @PostMapping("shop")
    public Response<Shop> createShop(@RequestBody Shop shop, HttpServletResponse response) {
        clean(shop);
        response.setStatus(HttpStatus.CREATED.value());
        try {
            return Response.of(shopService.createShop(shop));
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }

    private void clean(Shop shop) {
        shop.setOwnerUserId(UserContext.getCurrentUser().getId());
        shop.setId(null);
        shop.setCreatedAt(new Date());
        shop.setUpdatedAt(new Date());
    }

    // @formatter:off

    /**
     * @api {PATCH} /shop/:id 修改店铺
     * @apiName UpdateShop
     * @apiGroup 店铺
     * @apiParam {Number} id 店铺ID
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParamExample {json} Request-Example:
     * {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * }
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "data": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * }
     * }
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 404 Not Found 若店铺未找到
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试修改非自己管理店铺
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     * @param shopId
     * @param shop
     * @param response
     * @return 返回修改后的店铺信息
     */
    // @formatter:on
    @PostMapping("shop/{shopId}")
    public Response<Shop> updateShop(@PathVariable Long shopId, @RequestBody Shop shop, HttpServletResponse response) {
        try {
            return Response.of(shopService.updateShopById(shopId, shop));
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }

    // @formatter:off

    /**
     * @api {DELETE} /shop/:id 删除店铺
     * @apiName DeleteShop
     * @apiGroup 店铺
     * @apiHeader {String} Accept application/json
     * @apiParam {Number} id 店铺ID
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 204 No Content
     * {
     * "data": {
     * "id": 12345,
     * "name": "我的店铺",
     * "description": "我的苹果专卖店",
     * "imgUrl": "https://img.url",
     * "ownerUserId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * }
     * }
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 404 Not Found 若店铺未找到
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试删除非自己管理店铺
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    /**
     * @param shopId
     * @param response
     * @return 返回被删除的商店
     */
    // @formatter:on
    @DeleteMapping("shop/{shopId}")
    public Response<Shop> deleteShop(@PathVariable Long shopId, HttpServletResponse response) {
        try {
            return Response.of(shopService.deleteShopById(shopId));
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }
}
