package controller

import (
	"log"
	"os"

	"github.com/joho/godotenv"
)

func LoadEnv(input string) string {
	err := godotenv.Load("API/catatan.env")
	if err != nil {
		log.Fatalf(err.Error())
	}
	return os.Getenv(input)
}
