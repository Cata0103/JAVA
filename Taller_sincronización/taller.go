package main

import (
	"fmt"
	"math"
	"math/rand"
	"runtime"
	"sync"
	"time"
)

func puntosDentroDelCirculo(puntoX float64, puntoY float64) float64 {
	return math.Sqrt((math.Pow(puntoX,2.0) + math.Pow(puntoY,2.0)))
}

func monteCarlo(puntos_circulo float64, lista_len float64) float64{
	var pi = 4 * (puntos_circulo/lista_len)
	return pi 
}

func main() {
	start := time.Now()
	size := 100000000
	workerCount := runtime.NumCPU()
	batchSize := 10000
	var pi_monte_carlo float64
	jobs := make(chan int, workerCount)
	resultado := make(chan int, workerCount)
	var wg sync.WaitGroup

	for w := 0; w < workerCount; w++ {
		wg.Add(1)
		go func(id int) {
			defer wg.Done()
			puntosInternos := 0
			for batch := range jobs {
				for i := 0; i < batch; i++ {
					x := puntosDentroDelCirculo(rand.Float64() * 2 - 1 ,rand.Float64() * 2 - 1 )
					if x <= 1 {
						puntosInternos++
					}
				}
			}
			resultado <- puntosInternos
		}(w)
	}

	puntosFaltantes := size
	for puntosFaltantes > 0 {
		b := batchSize
		if puntosFaltantes < b {
			b = puntosFaltantes
		}
		jobs <- b
		puntosFaltantes -= b
	}
	close(jobs)


	go func() {
		wg.Wait()
		close(resultado)
	}()

	totalInternos := 0
	for v := range resultado {
		totalInternos += v
	}

	pi_monte_carlo = monteCarlo(float64(totalInternos), float64(size))
	fmt.Println("puntos dentro:", totalInternos)
	fmt.Println("total puntos:", size)
	fmt.Println("pi =", pi_monte_carlo)
	timeElapsed := time.Since(start)
    fmt.Println("Tiempo de ejecución:", timeElapsed)
}