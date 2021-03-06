package com.lee.weichatmall.controller;

import com.lee.weichatmall.domain.Goods;
import com.lee.weichatmall.domain.responesesEntity.PageResponse;
import com.lee.weichatmall.domain.responesesEntity.Response;
import com.lee.weichatmall.service.GoodsService;
import com.lee.weichatmall.service.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class GoodsController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    // @formatter:off

    /**
     * @api {get} /goods 获取所有商品
     * @apiName GetGoods
     * @apiGroup 商品
     * @apiHeader {String} Accept application/json
     * @apiParam {Number} pageNum 页数，从1开始
     * @apiParam {Number} pageSize 每页显示的数量
     * @apiParam {Number} [shopId] 店铺ID，若传递，则只显示该店铺中的商品
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     * @apiSuccess {Goods} data 商品列表
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "pageNum": 1,
     * "pageSize": 10,
     * "totalPage": 5,
     * "data": [
     * {
     * "id": 12345,
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "price": 500,
     * "stock": 10,
     * "shopId": 12345,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * },
     * {
     * ...
     * }
     * ]
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
     * @param pageNum
     * @param pageSize
     * @param shopId
     * @param response
     * @return 分页数据
     */
    // @formatter:on
    @RequestMapping("/goods")
    public PageResponse<Goods> getGoods(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam(value = "shopId", required = false) Long shopId,
                                        HttpServletResponse response) {
        PageResponse<Goods> pageResponse;
        try {
            pageResponse = goodsService.getGoodsByPage(pageNum, pageSize, shopId);
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return PageResponse.newInstance(0, 1, null, 0);
        }
        return pageResponse;
    }

    // @formatter:off
    /**
     * @api {post} /goods 创建商品
     * @apiName CreateGoods
     * @apiGroup 商品
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample {json} Request-Example:
     *          {
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "shopId": 12345
     *          }
     *
     *
     * @apiSuccess {Goods} data 创建的商品
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "shopId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试创建非自己管理店铺的商品
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */

    /**
     * @param goods    goods to be created
     * @param response the HTTP response
     * @return the newly created goods
     */
    // @formatter:on
    @PostMapping("/goods")
    public Response<Goods> createdGoods(@RequestBody Goods goods, HttpServletResponse response) {
        clean(goods);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            return Response.of(goodsService.createGoods(goods));
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }

    private void clean(Goods goods) {
        goods.setId(null);
        goods.setCreatedAt(new Date());
        goods.setUpdatedAt(new Date());
    }

    // @formatter:off

    /**
     * @param goodsId  the goods id to be deleted
     * @param goods    the goods new info
     * @param response the HTTP response
     * @return response info
     * @api {patch} /goods/:id 更新商品
     * @apiName UpdateGoods
     * @apiGroup 商品
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     * @apiParam {Number} id 商品ID
     * @apiParamExample {json} Request-Example:
     * {
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "price": 500,
     * "stock": 10
     * }
     * @apiSuccess {Goods} data 更新后的商品
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     * "data": {
     * "id": 12345,
     * "name": "肥皂",
     * "description": "纯天然无污染肥皂",
     * "details": "这是一块好肥皂",
     * "imgUrl": "https://img.url",
     * "price": 500,
     * "stock": 10,
     * "createdAt": "2020-03-22T13:22:03Z",
     * "updatedAt": "2020-03-22T13:22:03Z"
     * }
     * }
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试修改非自己管理店铺的商品
     * @apiError 404 Not Found 若商品未找到
     * @apiErrorExample Error-Response:
     * HTTP/1.1 401 Unauthorized
     * {
     * "message": "Unauthorized"
     * }
     */
    // @formatter:on
    @PostMapping("goods/{id}")
    public Response<Goods> updateGoods(@PathVariable("id") Long goodsId, @RequestBody Goods goods, HttpServletResponse response) {
        clean(goods);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            Goods updateGoods = goodsService.updateGoodsById(goodsId, goods);
            return Response.of(updateGoods);
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }

    // @formatter:off
    /**
     * @api {delete} /goods/:id 删除商品
     * @apiName DeleteGoods
     * @apiGroup 商品
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParam {Number} id 商品ID
     *
     * @apiSuccess {Goods} data 被删除的商品
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 204 No Content
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求中包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试删除非自己管理店铺的商品
     * @apiError 404 Not Found 若商品未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */

    /**
     * @param goodsId  the goods id to be deleted
     * @param response the HTTP response
     * @return the deleted goods
     */
    // @formatter:on
    @DeleteMapping("/goods/{id}")
    public Response<Goods> deleteGoods(@PathVariable("id") Long goodsId, HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return Response.of(goodsService.deleteGoodsById(goodsId));
        } catch (HttpException e) {
            response.setStatus(e.getHttpStatusCode());
            return Response.of(e.getMessage(), null);
        }
    }
}
