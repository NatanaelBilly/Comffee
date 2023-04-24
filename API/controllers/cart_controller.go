package controllers

import (
	"fmt"
	"log"
	"net/http"

	"github.com/Comffee/model"
	"github.com/gorilla/mux"
)

const id_cart int = 1

func GetCartId(w http.ResponseWriter, r *http.Request) (int, error) {
	db := Connect()
	defer db.Close()

	params := mux.Vars(r)
	user_id := params["user_id"]

	query := "SELECT cart_id FROM cart WHERE user_id = ?"
	var cartID int
	err := db.QueryRow(query, user_id).Scan(&cartID)
	if err != nil {
		return 0, err
	}

	return cartID, nil
}

func CartItemByCartId(w http.ResponseWriter, r *http.Request) {
	db := Connect()
	defer db.Close()

	rows, err := db.Query("SELECT cartitem_id, cartitem.quantity item_id, item_name, description, price, picture FROM item join cartitem on item.item_id=cartitem.item_id WHERE cartitem.cart_id=?;", id_cart)
	if err != nil {
		log.Println("(ERROR)\t", err)
		SendErrorResponse(w, 500)
		return
	}
	var cartitems model.Cartitem
	for rows.Next() {
		if err := rows.Scan(&cartitems.Cartitem_id, &cartitems.Cart_id, &cartitems.Quantity); err != nil {
			fmt.Println(err.Error())
		} else {

		}
	}

}

func RemoveItem(w http.ResponseWriter, r *http.Request) {
	db := Connect()
	defer db.Close()

	// Retrieve the item ID from the URL parameter
	params := mux.Vars(r)
	itemID := params["item_id"]

	var err error
	// Delete the item with the given item ID from the cart_items table for the current cart ID
	query := "DELETE FROM cart_items WHERE cart_id = ? AND item_id = ?"
	_, err = db.Exec(query, id_cart, itemID)
	if err != nil {
		// If there was an error deleting the item from the cart, return a 500 Internal Server Error
		log.Println("(ERROR)\t", err)
		w.WriteHeader(http.StatusInternalServerError)
		return
	} else {
		SendSuccessResponse(w)
	}
}
func TambahQuantityItem(w http.ResponseWriter, r *http.Request) {
	db := Connect()
	defer db.Close()

	param := mux.Vars(r)
	id_item := param["id_item"]

	_, errQuery := db.Exec("UPDATE cartitem SET quantity = quantity + 1 WHERE cart_id =? AND item_id =?", id_cart, id_item)

	if errQuery == nil {
		SendSuccessResponse(w)
	} else {
		log.Println("(ERROR)\t", errQuery.Error())
		SendErrorResponse(w, 400)
	}
}

func KurangQuantityItem(w http.ResponseWriter, r *http.Request) {
	db := Connect()
	defer db.Close()

	param := mux.Vars(r)
	id_item := param["id_item"]

	_, errQuery := db.Exec("UPDATE cartitem SET quantity = quantity - 1 WHERE cart_id =? AND item_id =?", id_cart, id_item)

	if errQuery == nil {
		SendSuccessResponse(w)
	} else {
		log.Println("(ERROR)\t", errQuery.Error())
		SendErrorResponse(w, 400)
	}
}

func TotalHarga(w http.ResponseWriter, r *http.Request) int {
	db := Connect()
	defer db.Close()

	var totalHarga int64
	err := db.QueryRow("SELECT SUM(item.harga * cartitem.quantity) AS total_harga from cartitem join item on cartitem.id_item=item.id_item where cartitem.cart_id=?", id_cart)

	if err != nil {
		log.Println(err)
		http.Error(w, "Failed to get total harga", http.StatusInternalServerError)
		return -1
	}

	fmt.Fprintf(w, "Total harga: %d", totalHarga)
	return int(totalHarga)
}
