package model

type item struct {
	Item_id     int    `json:"item_id"`
	Item_name   string `json:"item_name"`
	Description string `json:"description"`
	Price       int    `json:"price"`
	Picture     string `json:"picture"`
}

type cart struct {
	Cart_id int `json:"cart_id"`
	User_id int `json:"user_id"`
}

type cartitem struct {
	Cartitem_id int `json:"cartitem_id"`
	Cart_id     int `json:"cart_id"`
	Item_id     int `json:"item_id"`
	Quantity    int `json:"quantity"`
}
