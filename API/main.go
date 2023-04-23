package main

import (
	"log"
	"net/http"

	"github.com/Comffee/API/controllers"
	"github.com/gorilla/mux"
	"github.com/rs/cors"
)

func main() {
	log.Println("RUNNING ...")
	router := mux.NewRouter()

	// router.HandleFunc("/Login", controllers.Login).Methods("POST")
	// router.HandleFunc("/Logout", controllers.Logout).Methods("GET")
	// router.HandleFunc("/Register", controllers.Register).Methods("POST")

	// router.HandleFunc("/LupaPassword", controllers.Authenticate(controllers.LupaPassword, 1)).Methods("PUT")
	// router.HandleFunc("/UpdateProfil/{id_pengguna}", controllers.PerbaruiProfil).Methods("PUT")

	// router.HandleFunc("/LihatKupon/{id_pengguna}", controllers.LihatKupon).Methods("GET")
	// router.HandleFunc("/LihatKupon/{id_pengguna}/{tipe_tiket}", controllers.LihatKuponBerdasarkanTipeTiket).Methods("GET")

	// router.HandleFunc("/Pesawat/{tempat_berangkat}/{tujuan_berangkat}/{tanggal_berangkat}", controllers.LihatListPesawat).Methods("GET")
	// router.HandleFunc("/Hotel/{kota}/{tanggal_inap}/{akhir_inap}/{jumlah_penginap}", controllers.LihatListHotel).Methods("GET")

	// router.HandleFunc("/Pesawat/Kursi/{id_pesawat}", controllers.LihatListKursiPesawat).Methods("GET")
	// router.HandleFunc("/Hotel/Kamar/{id_hotel}", controllers.LihatListKamarHotel).Methods("GET")

	// router.HandleFunc("/Pesawat/Kursi", controllers.PesanKursiPesawat).Methods("POST")
	// router.HandleFunc("/Hotel/Kamar", controllers.PesanKamarHotel).Methods("POST")

	// router.HandleFunc("/SelesaiPesanPesawat", controllers.SelesaiPesanPesawat).Methods("PUT")
	// router.HandleFunc("/SelesaiPesanHotel", controllers.SelesaiPesanHotel).Methods("PUT")

	// router.HandleFunc("/BatalPesanPesawat", controllers.BatalPesanPesawat).Methods("PUT")
	// router.HandleFunc("/BatalPesanHotel", controllers.BatalPesanHotel).Methods("PUT")

	// router.HandleFunc("/LihatHistoryPesawat/{id_pengguna}", controllers.MelihatHistoryPesawat).Methods("GET")
	// router.HandleFunc("/LihatHistoryHotel/{id_pengguna}", controllers.MelihatHistoryHotel).Methods("GET")

	svrPort := controllers.LoadEnv("SVR_PORT")
	log.Println("Connected to port " + svrPort)
	addr := ":" + svrPort

	corsHandler := cors.New(cors.Options{
		AllowedOrigins:   []string{"localhost:8080,https://comffee.com"},
		AllowedMethods:   []string{"GET", "POST", "PUT", "DELETE"},
		AllowCredentials: true,
	})
	handler := corsHandler.Handler(router)

	log.Fatal(http.ListenAndServe(addr, handler))
	log.Fatal(http.ListenAndServe(addr, router))

	http.Handle("/", router)
}
