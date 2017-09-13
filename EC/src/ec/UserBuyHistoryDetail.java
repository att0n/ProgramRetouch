package ec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 *
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		int buy_id = Integer.parseInt(request.getParameter("buy_id"));

		BuyDAO buyDao = new BuyDAO();
		BuyDetailDAO buyDetailDao = new BuyDetailDAO();
		List<ItemDataBeans> idb = new ArrayList<ItemDataBeans>();

		try {

			idb = buyDetailDao.getBuyItemDetailListByBuyId(buy_id);
			BuyDataBeans b = buyDao.getBuyDataBeansById2(buy_id);

			request.setAttribute("buyDetailList", idb);
			request.setAttribute("buy", b);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

	}
}
