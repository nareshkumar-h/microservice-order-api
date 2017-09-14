package com.naresh.orderappmicroservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naresh.orderappmicroservice.dto.Book;
import com.naresh.orderappmicroservice.dto.OrderDTO;
import com.naresh.orderappmicroservice.dto.UserDTO;
import com.naresh.orderappmicroservice.external.BookServiceClient;
import com.naresh.orderappmicroservice.external.UserServiceClient;
import com.naresh.orderappmicroservice.model.Order;
import com.naresh.orderappmicroservice.service.OrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private BookServiceClient bookServiceClient;

	@Autowired
	private UserServiceClient userServiceClient;

	@GetMapping
	public List<OrderDTO> getList(@RequestHeader("Authorization") String authorizationToken ) {

		List<Order> list = orderService.list();
		List<OrderDTO> orderList = new ArrayList<>();

		for (Order o : list) {

			OrderDTO orderDto = convertTo(authorizationToken,o);
			orderList.add(orderDto);
		}

		return orderList;
	}

	@GetMapping("/myorders")
	public List<OrderDTO> getMyOrders(@RequestHeader("Authorization") String authorizationToken) {

		UserDTO user = userServiceClient.getCurrentUser(authorizationToken);

		List<Order> list = orderService.findByUserId(user.getId());
		List<OrderDTO> orderList = new ArrayList<>();

		for (Order o : list) {

			OrderDTO orderDto = convertTo(authorizationToken, o, user);
			orderList.add(orderDto);
		}

		return orderList;
	}

	private OrderDTO convertTo(String authorizationToken, Order o) {
		UserDTO user = userServiceClient.getUser( o.getUserId());
		Book book = bookServiceClient.getBook( o.getBookId());
		OrderDTO orderDto = new OrderDTO(o, user, book);
		return orderDto;
	}

	private OrderDTO convertTo(String authorizationToken, Order o, UserDTO user) {
		Book book = bookServiceClient.getBook( o.getBookId());
		OrderDTO orderDto = new OrderDTO(o, user, book);
		return orderDto;
	}

}
