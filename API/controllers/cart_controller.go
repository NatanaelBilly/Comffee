package controllers

import (
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

func getCartId(w http.ResponseWriter, r *http.Request) {
	db := Connect()
	defer db.Close()

	param := mux.Vars(r)
	user_id := param["user_id"]
	query := "SELECT cart_id from cart where user_id="+user_id+";"
	rows, err := db.Query(query)
	if err != nil {
		log.Println("(ERROR)\t", err)
		SendErrorResponse(w, 500)
		return
	}
}
