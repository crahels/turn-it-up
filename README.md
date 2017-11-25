# <div style="text-align: center"><span>Trilogy</span> - Turn It Up</div> 

### <div style="text-align: center">IF3111 Pengembangan Aplikasi Berbasis Platform Khusus</div>

---
## <span>Deskripsi Aplikasi Turn It Up</span>

![](android/TurnItUp/app/src/main/ic_turnituplogo-web.png)

<div style="text-align: justify">Pada Tugas Besar ini, kami membuat sebuah permainan rhythm game dengan menggabungkan 3 buah subsistem yaitu Android, Unity, dan Arduino. Android digunakan untuk menampilkan menu permainan seperti Profile, Show Highscores, Select Song, Help, dan How to Play. Unity digunakan sebagai layar permainan yang akan menampilkan notes yang harus ditekan oleh pemain untuk mendapatkan score dan juga memberikan animasi 3D karakter pemain. Arduino berperan sebagai konsol untuk mendapatkan input pengguna untuk memilih karakter yang akan digunakan pada permainan.</div>

## <span>Anggota Tim</span>

Berikut ini adalah anggota tim pengembang aplikasi Turn It Up:
1. [Roselina](http://gitlab.informatika.org/roselinapradjanata) :smile: - 13515034
2. [Ida Ayu Putu Ari Crisdayanti](http://gitlab.informatika.org/dayu_ari) :dancers: - 13515067
3. [Rachel Sidney Devianti](http://gitlab.informatika.org/crahels) :yum: - 13515124

## <span>Sistem</span>

Berikut ini adalah sistem yang digunakan untuk membangun aplikasi Turn It Up:
1. <span>**Android**</span>
2. <span>**Unity**</span>
3. <span>**Arduino**</span>

## <span>Pembahasan Sistem</span>

### <span>Deskripsi Umum Sistem</span>

<div style="text-align: justify">Sistem yang dirancang merupakan sebuah sistem berbasis game yang mengadopsi fungsionalitas game “Step Master”. Sistem dibangun di atas 3 platform berbeda yaitu Android, Unity, dan Arduino. Secara umum, sistem memfasilitasi pengguna untuk bermain tap game sambil mendengarkan musik. Sistem menerima input username pemain yang kemudian disimpan pada basis data sebagai user yang sedang login. Sistem memberikan menu pilihan judul lagu yang dapat dimainkan oleh pengguna. Setelah lagu dipilih, sistem akan memutar musik dan memunculkan simbol-simbol panah (atas, bawah, kiri, kanan) sesuai ritme lagu. Pengguna harus memasukkan input dengan menekan media input seperti tombol atau keyboard. Jika input yang diberikan sesuai dan tepat saat simbol panah muncul maka pengguna akan memeroleh skor yang diakumulasikan setelah lagu berakhir dan disimpan ke basis data. Skor ini nantinya digunakan untuk menampilkan nama-nama pemain dengan skor tertinggi. </div>

<div style="text-align: justify">Khususnya pada platform Android dan Arduino, tidak keseluruhan fungsionalitas sistem dapat diimplementasikan saat plaftorm tersebut berdiri sendiri. Setiap platform juga memiliki beberapa fungsionalitas sistem yang tidak terdapat pada platform lainnya. Pada platform Android, sistem dapat melakukan autentikasi pengguna serta menampilkan lirik lagu dengan cara terhubung ke browser. Pada platform Arduino, sistem dapat mendeteksi keberadaan objek (pemain/pengguna) dengan menggunakan sensor jarak. Pada platform Unity, sistem dapat menampilkan avatar 3 dimensi yang bergerak mengikuti input pengguna pada keyboard.</div>

### <span>Panduan Instalasi Sistem</span>

#### Android
Berikut ini adalah panduan instalasi Android pada device:
1. Bukalah Project TurnItUp pada folder android di Android Studio.
2. Sambungkan device dengan PC/laptop menggunakan kabel data.
3. Pilih "Run" --> "Run app" pada project.
4. Program akan di-install ke device yang bersangkutan dan siap untuk dijalankan.
5. Adapun jika ingin menjalankan aplikasi, pastikan Internet dan Location Service telah di-setting pada device yang bersangkutan untuk aplikasi TurnItUp.

#### Arduino
Berikut ini langkah-langkah untuk mengunggah program Arduino pada Arduino:
1. Sambungkan kabel USB ke Arduino.
2. Buka source code pada software Arduino.
3. Pada Menu Tools, pilih Boards, pilih Arduino/Genuino Uno.
4. Pada Menu Tools, pilih Ports, pilih port yang dipakai oleh Arduino.
5. Pilih Upload.

Berikut ini langkah-langkah untuk pairing bluetooth PC dengan module bluetooth HC-05 sekaligus menjalankan Unity:
1. Hubungkan Arduino dengan baterai atau power bank.
2. Buka Settings pada PC, pilih Bluetooth, nyalakan Bluetooth, pilih More Bluetooth Options.
3. Pada tab COMPorts, pilih Add, pilih Outgoing, pilih Browse.
4. Pilih HC-05.
5. Lalu pilih OK.
6. Buka Device Manager.
7. Pilih Ports.
8. Pilih Standard Serial over Bluetooth Link.
9. Pilih Tab Port Settings.
10. Piih Advanced.
11. Ganti COM Port Number menjadi COM(nomor sesuai port Unity).
12. Sesuaikan nomor port Arduino pada program Unity agar aplikasi Unity dapat tersambung pada bluetooth.

#### Unity
Berikut ini adalah panduan instalasi aplikasi Unity pada PC/laptop:
1. Buka project TurnItUp pada folder unity di Unity.
2. Pilih File.
3. Pilih Build Settings.
4. Pilih semua scene yang digunakan pada aplikasi.
5. Pilih "Build" --> "Choose Directory" --> "Save".
6. Aplikasi TurnItUp siap untuk dijalankan.

### <span>Cara Menjalankan Sistem</span>

<div style="text-align: justify">Pada saat ingin menjalankan sistem, pastikan seorang pengguna telah terdaftar pada aplikasi. Apabila belum terdaftar pengguna dapat melakukan register melalui aplikasi Android dan melakukan login. Setelah melakukan login, pengguna dapat memainkan permainan pada Unity. Pengguna memilih karakter pada permainan dengan menggunakan Arduino kemudian pengguna dapat menggunakan Key Down, Key Up, Key Left, dan Key Right untuk bermain dan menggerakkan karakter. Setelah pengguna selesai bermain, pengguna dapat mengecek apakah termasuk ke dalam 10 pemain dengan highscore tertinggi pada aplikasi Android. Selain itu, Arduino akan menampilkan jarak pengguna terhadap console dan menampilkan pesan "Welcome" kepada pengguna. Detail dari fungsionalitas aplikasi ini telah dijelaskan pada proposal final.</div>

<div style="text-align: justify">Khusus untuk aplikasi Android, terdapat informasi menarik yang perlu Anda ketahui. Lagu-lagu pada "Select Music" untuk user baru pada awalnya di-lock. Terdapat 3 buah lagu yang dapat dibuka dengan cara yang berbeda-beda, yaitu dengan menggunakan sensor magnet (> 30), sensor cahaya (> 100), dan Google Location Service (apabila berada di Labtek V).</div>

## <span>Gameplay</span>
Berikut ini adalah alur permainan pada aplikasi TurnItUp:
1. Pemberian Skor
   Jika pemain berhasil menekan tombol yang benar dalam waktu yang tepat, maka pemain akan mendapatkan nilai. Jika pemain menekan tombol yang salah atau menekan tombol yang benar tetapi waktunya terlalu meleset, maka pemain tidak akan mendapat skor (miss).
2. Combo
   Pemain yang berhasil menekan tombol dengan benar secara berturut-turut akan mendapatkan tambahan combo. Combo digunakan untuk mempercepat penambahan skor karena semakin besar combo, maka skor yang didapatkan pemain setiap berhasil menekan tombol dengan benar akan dikalikan dengan nilai combo yang semakin besar. Combo yang dimiliki pemain akan berubah kembali menjadi nol jika pemain tidak berhasil menekan tombol dengan benar dan selanjutnya perhitungan combo dimulai dari awal.
3. Life Bar
   Saat permainan berlangsung akan terdapat life bar yang berfungsi untuk mengetahui tingkat energi pemain. Pemain yang berhasil menekan tombol dengan benar secara terus menerus akan mempunyai lebih banyak energi. Energi yang didapat mempunyai nilai maksimum, sehingga jika pada suatu titik energi pemain sudah berada pada nilai maksimum maka energi tidak dapat bertambah lagi. Lalu sebaliknya, pemain yang tidak berhasil menekan tombol dengan benar akan kehilangan energi.
4. Game Over
   Permainan akan berhenti jika salah satu kondisi ini tercapai:
   1. Pemain berhasil memenangkan permainan (sebuah lagu berhasil diselesaikan).
   2. Pemain tidak berhasil menekan tombol dengan benar secara terus-menerus sehingga life bar mencapai nol.

## <span>Link Github</span>
Kode final pengembangan aplikasi TurnItUp dapat dilihat pada link berikut: [TurnItUp](http://gitlab.informatika.org/IF3111-2017-06/turnitup)

## <span>Video Pengenalan Aplikasi</span> 
Video pengenalan aplikasi dapat dilihat pada link berikut ini: [TurnItUp Demo](https://drive.google.com/drive/folders/1IAzYHU0GiLbFYoceQNF2KWBp8d86GYY-?usp=sharing)
