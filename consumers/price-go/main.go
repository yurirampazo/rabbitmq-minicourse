package main

import (
	"encoding/json"
	"fmt"

	"github.com/streadway/amqp"
)

func main() {
	fmt.Println("GO Consumer Application")
	conn, err := amqp.Dial("amqp://admin:123456@localhost:5672/")
	if err != nil {
		fmt.Println(err)
		panic(err)
	}

	defer conn.Close()

	ch, err := conn.Channel()
	if err != nil {
		fmt.Println(err)
		panic(err)
	}
	defer ch.Close()

	msgs, err := ch.Consume(
		"PRICE",
		"",
		true,
		false,
		false,
		false,
		nil,
	)
	if err != nil {
		fmt.Println("Failed to register a consumer:", err)
		panic(err)
	}

	forever := make(chan bool)
	go func() {
		for d := range msgs {
			fmt.Printf("Received message: %s\n", d.Body)

			var msg PriceMessage
			err := json.Unmarshal(d.Body, &msg)
			if err != nil {
				fmt.Println("Error unsmarshalling JSON", err)
				continue
			}
			fmt.Printf("Received Product %s, coasts %.2f\n", msg.ProductCode, msg.Price)
		}
	}()

	fmt.Println("Successfully connected to our RabbitMQ instance")
	fmt.Println(" [*] - waiting for messages")
	<- forever


}

type PriceMessage struct {
    ProductCode string  `json:"productCode"`
    Price  float64 `json:"price"`
}
