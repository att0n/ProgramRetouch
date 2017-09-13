package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.DeliveryMethodDataBeans;
import beans.ItemDataBeans;
import dao.DeliveryMethodDAO;

/**
 * 購入商品確認画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/BuyConfirm")
public class BuyConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int addPrice;
		String DeliveryMethodName;

		try {
			//選択された配送方法IDを取得
			int inputDeliveryMethodId = Integer.parseInt(request.getParameter("delivery_method_id"));
			if(inputDeliveryMethodId==1) {
				DeliveryMethodName = "特急配送";
				addPrice = 500;
			}else if(inputDeliveryMethodId==2) {
				DeliveryMethodName = "通常配送";
				addPrice = 0;
			}else {
				DeliveryMethodName = "日時指定配送";
				addPrice = 200;
			}

			//選択されたIDをもとに配送方法Beansを取得
			DeliveryMethodDataBeans userSelectDMB = DeliveryMethodDAO.getDeliveryMethodDataBeansByID(inputDeliveryMethodId);
			//買い物かご
			ArrayList<ItemDataBeans> cartIDBList = (ArrayList<ItemDataBeans>) session.getAttribute("cart");
			System.out.println(cartIDBList.get(0).getName());

			//合計金額
			int totalPrice = EcHelper.getTotalItemPrice(cartIDBList)+addPrice;

			BuyDataBeans bdb = new BuyDataBeans();

			bdb.setUserId((int) session.getAttribute("userId"));
			bdb.setTotalPrice(totalPrice);
			bdb.setDelivertMethodId(userSelectDMB.getId());
			bdb.setDeliveryMethodName(DeliveryMethodName);
			bdb.setDeliveryMethodPrice(addPrice);

			//購入確定で利用
			session.setAttribute("bdb", bdb);
			request.getRequestDispatcher(EcHelper.BUY_CONFIRM_PAGE).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}

}
