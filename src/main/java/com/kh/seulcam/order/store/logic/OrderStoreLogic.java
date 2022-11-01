package com.kh.seulcam.order.store.logic;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.seulcam.member.domain.Member;
import com.kh.seulcam.order.domain.Order;
import com.kh.seulcam.order.domain.OrderPay;
import com.kh.seulcam.order.domain.OrderProduct;
import com.kh.seulcam.order.store.OrderStore;
import com.kh.seulcam.point.domain.Point;
import com.kh.seulcam.product.domain.Product;

@Repository
public class OrderStoreLogic implements OrderStore {

	@Override
	public Member printMemberInfo(SqlSession session, Member memberId) {
		Member member = session.selectOne("OrderMapper.selectMemberInfo",memberId);
		return member;
	}

	@Override
	public int changeAddress(SqlSession session, Member member) {
		int result = session.update("OrderMapper.updateAddress",member);
		return result;
	}

	@Override
	public List<OrderProduct> printProductInfo(SqlSession session, Member memberId) {
		List<OrderProduct>oList = session.selectList("OrderMapper.selectProductInfo",memberId);
		return oList;
	}

	//장바구니에서상품 불러온거
	@Override
	public List<Product> selectProduct(SqlSession session, int productNo) {
		List<Product>pList = session.selectList("CartMapper.selectAllProduct",productNo);
		return pList;
	}

	//주문 등록
	@Override
	public int registOrder(SqlSession session,Order order) {
		int result = session.insert("OrderMapper.insertOrder",order);
		return result;
	}



	//주문 상품들에게 주문번호 넣고 주문상태 Y로 변경
	@Override
	public int registOrderNo(SqlSession session, int orderNo,String memberId) {
		
		  HashMap<String,String>paramMap=new HashMap<String,String>();
		  paramMap.put("orderNo",Integer.toString(orderNo) );
		  paramMap.put("memberId",memberId);
		 		
		int result = session.update("OrderMapper.updateOrderProduct",paramMap);
		return result;
	}

	//주문 완료 정보 가져오기
	@Override
	public Order printOrder(SqlSession session, Integer orderNo) {
		Order order = session.selectOne("OrderMapper.selectPrintOrder",orderNo);
		return order;
	}

	@Override
	public int registPay(SqlSession session, OrderPay orderPay) {
		int result = session.insert("OrderMapper.insertPay",orderPay);
		return result;
	}

	@Override
	public OrderPay printOrderPayInfo(SqlSession session, Integer orderNo) {
		OrderPay orderPay=session.selectOne("OrderMapper.selectPrintPay",orderNo);
		return orderPay;
	}

	@Override
	public int changeCompleteAddress(SqlSession session, Order order) {
		int result = session.update("OrderMapper.updateCompleteAddress",order);
		return result;
	}

	@Override
	public List<OrderProduct> printCompleteProduct(SqlSession session, Integer orderNo) {
		List<OrderProduct>pList = session.selectList("OrderMapper.selectProduct",orderNo);
		return pList;
	}

	@Override
	public List<Order> printCompleteList(SqlSession session, String memberId) {
		List<Order>oList=session.selectList("OrderMapper.selectCompleteList",memberId);
		return oList;
	}

	@Override
	public List<OrderPay> printOrderPay(SqlSession session, int orderNo) {
		List<OrderPay>opList=session.selectList("OrderMapper.selectPrintPay",orderNo);
		return opList;
	}

	@Override
	public List<Order> printAllOrder(SqlSession session) {
		List<Order>oList = session.selectList("OrderMapper.selectAllOrder");
		return oList;
	}

	@Override
	public int cngDilivary(SqlSession session, Order order) {
		int result=session.update("OrderMapper.updateDilivary",order);
		return result;
	}
	
	//포인트 테이블 저장//맴버테이블에 반영
	@Override
	public int registPoint(SqlSession session, Point point) {
		int result=session.insert("PointMapper.insertUsePoint",point);
		int result1=session.insert("OrderMapper.updateUsePoint",point);
		return result;
	}

	@Override
	public int registGetPoint(SqlSession session, String point, String memberId) {
		HashMap<String,String>paramMap=new HashMap<String,String>();
		  paramMap.put("point",point );
		  paramMap.put("memberId",memberId);
		int result=session.insert("PointMapper.insertGetPoint",paramMap);
		int result1=session.insert("OrderMapper.updateGetPoint",paramMap);
		
		return result;
	}

	@Override
	public int updateDilivaryStatus(SqlSession session, Integer orderNo) {
		int result=session.update("OrderMapper.updateDilivaryStatus",orderNo);
		
		return result;
	}

}
