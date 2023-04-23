package model

type Item struct {
	Item_id     int    `json:"item_id"`
	Item_name   string `json:"item_name"`
	Description string `json:"description"`
	Price       int    `json:"price"`
	Picture     string `json:"picture"`
}

type Cart struct {
	Cart_id int `json:"cart_id"`
	User_id int `json:"user_id"`
}

type Cartitem struct {
	Cartitem_id int `json:"cartitem_id"`
	Cart_id     int `json:"cart_id"`
	Item_id     int `json:"item_id"`
	Quantity    int `json:"quantity"`
}

type ItemResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
	Data    []Item `json:"data"`
}

type CartitemResponse struct {
	Status  int        `json:"status"`
	Message string     `json:"message"`
	Data    []Cartitem `json:"data"`
}

type CartResponse struct {
	Status  int    `json:"status"`
	Message string `json:"message"`
	Data    []Cart `json:"data"`
}
