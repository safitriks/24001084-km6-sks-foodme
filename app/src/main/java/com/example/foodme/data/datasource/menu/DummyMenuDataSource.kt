package com.example.foodme.data.datasource.menu

import com.example.foodme.data.model.Menu

class DummyMenuDataSource : MenuDataSource{
    override fun getMenus(): List<Menu> {
        return listOf(
            Menu(
                imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/menu_img/img_ayam_bakar.jpg?raw=true",
                price = 50000.00,
                details = "Hidangan tradisional yang dibuat dengan cara memanggang potongan ayam yang telah direndam dalam bumbu khusus. Proses pemanggangan ini memberikan cita rasa yang unik, dengan perpaduan manis dan pedas yang meresap ke dalam daging ayam. Hidangan ini seringkali disajikan dengan nasi hangat dan sambal sebagai pelengkapnya, memberikan pengalaman makan yang memuaskan.",
                name = "Ayam Bakar",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/menu_img/img_ayam_goreng.jpg?raw=true",
                price = 40000.00,
                details = "Hidangan klasik yang terdiri dari potongan ayam yang digoreng hingga kecokelatan dan renyah di luar, namun tetap lembut dan juicy di dalamnya. Proses penggorengan ini memberikan cita rasa gurih yang khas, sering kali diperkaya dengan rempah-rempah lokal yang memberi aroma harum. Ayam goreng sering disajikan sebagai hidangan utama di berbagai kesempatan, mulai dari acara keluarga hingga restoran mewah.",
                name = "Ayam Goreng",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/menu_img/img_ayam_geprek.jpg?raw=true",
                price = 40000.00,
                details = "Hidangan populer yang berasal dari Indonesia. Ayam goreng yang digeprek (dihempaskan dengan menggunakan palu atau alat khusus) kemudian dilumuri dengan sambal pedas yang menggugah selera. Tekstur renyah dari ayam yang digeprek bertemu dengan pedasnya sambal, menciptakan kombinasi yang menggoda selera.",
                name = "Ayam Geprek",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/menu_img/img_sate_usus_ayam.jpg?raw=true",
                price = 5000.00,
                details = "Hidangan yang terdiri dari potongan-potongan usus ayam yang ditusuk dan dipanggang di atas bara api. Biasanya disajikan dengan bumbu kacang kental dan pedas, sate usus ayam merupakan sajian yang kaya akan protein dan cita rasa yang lezat. Hidangan ini sering ditemukan di warung-warung pinggir jalan dan restoran khas Indonesia.",
                name = "Sate Usus Ayam",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/menu_img/img_gurame_bakar.jpeg?raw=true",
                price = 35000.00,
                details = "Hidangan khas Indonesia yang menggoda dengan aroma harum dan cita rasa yang lezat. Ia terdiri dari ikan gurame segar yang dibumbui dengan rempah-rempah khas, kemudian dipanggang hingga matang sempurna. Kulitnya renyah, sementara dagingnya tetap lembut dan juicy. Gurame bakar sering disajikan dengan sambal terasi pedas dan nasi hangat, menciptakan paduan sempurna dari rasa gurih dan pedas yang memikat.",
                name = "Gurame Bakar",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgUrl = "https://github.com/safitriks/foodme-assets/blob/main/menu_img/img_gurame_asam_manis.jpeg?raw=true",
                price = 35000.00,
                details = "Hidangan yang menggabungkan kelezatan ikan gurame dengan kombinasi rasa manis dan asam yang menyegarkan. Ikan gurame digoreng hingga kecokelatan dan kemudian disiram dengan saus asam manis yang khas. Sausnya terbuat dari campuran gula, cuka, dan bumbu-bumbu pilihan yang memberikan cita rasa yang seimbang antara manis dan asam. Potongan bawang bombay, paprika, dan sayuran lainnya menambah kesegaran dan aroma yang menggugah selera. Gurame asam manis adalah hidangan yang menyenangkan dan menggugah selera.",
                name = "Gurame Asam manis",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/safitriks/foodme-assets/main/menu_img/img_udang_goreng.webp",
                price = 55000.00, name = "Udang Goreng",
                details = "Hidangan laut yang terdiri dari udang segar yang dibalut dengan tepung dan digoreng hingga keemasan. Hidangan ini memiliki rasa gurih yang khas dari udang, dengan tekstur renyah di luar dan lembut di dalam. Udang goreng seringkali disajikan dengan saus sambal atau saus tartar sebagai pelengkapnya, menambah kenikmatan dalam setiap gigitannya.",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(
                imgUrl = "https://raw.githubusercontent.com/safitriks/foodme-assets/main/menu_img/img_cumi_goreng.webp",
                price = 55000.00, name = "Cumi Goreng",
                details = "Hidangan laut yang terbuat dari cumi-cumi segar yang digoreng hingga kecokelatan. Cumi goreng memiliki tekstur yang kenyal di dalam dan renyah di luar, dengan citarasa gurih yang khas dari rempah-rempah dan tepung crispy yang digunakan dalam proses penggorengannya. Hidangan ini sering dinikmati sebagai makanan ringan atau sebagai hidangan utama.",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                locationUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
    }
}