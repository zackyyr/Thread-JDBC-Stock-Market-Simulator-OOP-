# Project Latihan Thread & JDBC - Interactive Stock Market Simulator

| | |
|---|---|
| **Nama** | M. Raechan Ulwan Zacky |
| **NIM** | F1D02310015 |

---

## Summary
Project **Stock Market Simulator** ini merupakan simulasi pasar saham berbasis **Java Thread** dan **JDBC (MySQL)**.  
Setiap saham dijalankan pada thread terpisah yang memperbarui harga secara paralel setiap beberapa detik.  
Perubahan harga disimpan ke database menggunakan JDBC, mensimulasikan perilaku *real-time stock trading system* seperti di dunia nyata.  

Aplikasi ini membantu memahami:
- **Multithreading**
- **Database interaction (JDBC)**

---

## Features
- Multi-threaded stock price simulation (setiap saham = 1 thread)
- Real-time update harga naik/turun
- Integrasi database MySQL dengan JDBC
- Penyimpanan riwayat harga ke tabel `price_history`
- Modular design dengan struktur OOP
- Dashboard console ASCII responsive
- (Optional) GUI Swing untuk tampilan harga saham live

---

## Folder Structure
```bash
src/
 ├─ db/
 │   └─ DatabaseManager.java       # Class untuk koneksi dan query JDBC
 ├─ model/
 │   └─ Stock.java                 # Class representasi data saham
 ├─ thread/
 │   └─ StockThread.java           # Thread untuk update harga saham
 └─ main/
     └─ StockMarketAppDashboard.java  # Entry point aplikasi utama
```

---

## Database Setup

1. **Buat Database & Tabel**

```sql
CREATE DATABASE IF NOT EXISTS stock_db;

USE stock_db;

CREATE TABLE IF NOT EXISTS stocks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
    current_price DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS price_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stock_id INT,
    price DOUBLE NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (stock_id) REFERENCES stocks(id)
);
```

2. **Insert Sample Data**
```sql
INSERT INTO stocks (symbol, name, current_price) VALUES
('BBCA', 'Bank Central Asia', 8900),
('TLKM', 'Telekomunikasi Indonesia', 4100),
('GOTO', 'GOTO Group', 450),
('AAPL', 'Apple Inc.', 175.20),
('GOOGL', 'Alphabet Inc.', 132.50),
('MSFT', 'Microsoft Corp.', 300.80),
('AMZN', 'Amazon.com Inc.', 138.40),
('TSLA', 'Tesla Inc.', 270.90),
('META', 'Meta Platforms', 210.55),
('NFLX', 'Netflix Inc.', 505.20),
('IBM', 'International Business Machines', 132.50),
('ORCL', 'Oracle Corp.', 95.75),
('INTC', 'Intel Corp.', 50.30),
('CSCO', 'Cisco Systems', 56.80),
('SAP', 'SAP SE', 145.20),
('ADBE', 'Adobe Inc.', 512.40),
('CRM', 'Salesforce.com', 210.15),
('UBER', 'Uber Technologies', 42.90),
('LYFT', 'Lyft Inc.', 28.65),
('SQ', 'Block Inc. (Square)', 65.80),
('NVDA', 'NVIDIA Corp.', 345.10),
('PYPL', 'PayPal Holdings', 72.50),
('BABA', 'Alibaba Group', 115.40);
```

---

## Cara Jalanin Program

1. **Pastikan MySQL berjalan**
```bash
brew services start mysql@8.0  # MacOS (Homebrew)
mysql -u root -p               # Login ke MySQL
```

2. **Compile Semua Kode Java**
```bash
javac -cp "lib/mysql-connector-j-9.5.0.jar:." db/*.java model/*.java thread/*.java main/*.java
```

3. **Jalankan Program**
```bash
java -cp "lib/mysql-connector-j-9.5.0.jar:." main.StockMarketAppDashboard
```

4. **Lihat Dashboard**
- Harga saham akan update real-time di console
- Header dan tabel disesuaikan otomatis sesuai jumlah saham
- Ctrl+C untuk stop

---

## 📸 Screenshots
- DEMO PROGRAM : 
  ![Demo Program](/img/LiveProgram.gif)  
- Console dashboard responsive dengan update harga saham  
  ![Program Jalan](/img/ProgramJalan.png)  
- MySQL table `stocks` & `price_history`  
  ![DB Connection](/img/DBConnection.png)  



---

© 2025 M. Raechan Ulwan Zacky | Universitas Mataram

