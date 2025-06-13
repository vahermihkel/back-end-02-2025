package ee.mihkel.veebipood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
public class VeebipoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeebipoodApplication.class, args);
	}

}

// Frontend:
//+++ Kategooriad Dropdowni
//+++ Signup
//+++Profile --> 1. küsime kasutajat back-endist  2. võimaldame kasutajat muuta
// Backend:
//+++Entity -> Order. OrderRepository. OrderController
// Frontend:
// +++Avalehel lisama tooteid ostukorvi.
// +++LocalStorage lisame tooteid, mis on ostukorvis.
// Lisame koguse LocalStorage
// Backend:
// +++Ostukorvi sisestamine. Kokkuarvutus andmebaasist
// Saadame toodete asemel tooted+kogused
// Frontend:
// +++Vaadata ostukorvi, kustuta ostukorvist, tühjenda ostukorvi
// +++Ostukorvi kokkuarvutus.
// +++Saadame Back-endi ostukorvi sisu -> paneme kasutaja külge.
// +++Lisame iga toote kõrvale koguse.
// ++++Kasutaja tellimused -> saab tema tellimusi vastavalt tokenile näha.

// +++URLi peitmised
// +++nuppude peitmised
// +++Kasutaja nimi Navbaris. useContext / redux
// +++Ostukorvi kogusumma Navbaris. useContext / redux

// +++Tarnija API päringud
// +++Pakiautomaadid API päring
// +++Makse osas API päring teise rakenduse back-endi
// +++@Bean
// +++ logimine
// +++ Rollid

// Frontend
// Frontendis Auth haldus. --> mingid nupud peita.+++

// +++Single Product leht
// +++Edit Product leht
// Tõlge+++
// Kaardirakendus (G Maps asemel Leaflet)+++

// Backend:
// logimine+++
// E-maili saatmine+++
// CRON+++
// erinevad profiilid+++

// Frontend:
// sobivad veateated.

// ! Redis, caching, CDN (cloud-care, external caching)
// ! RabbitMQ või muu message queue
// Kafka
// Event qeueing (event management)
// Web sockets
// Docker
// Kubernetes
// Sentry
// Ansible
// Amazon AWS

// 9. 11.03
//xx. 13.03
//10. 18.03
//11. 20.03
//xx. 25.03 ---> lükkame edasi
//xx. 27.03 ---> lükkame edasi
//xx. 01.04 --> allkirjalehele
//13. 03.04
//14. 08.04
//            09.04 ---> lõpp
//15. 10.04
//16. 15.04
//17. 17.04
//18. 24.04 --> lõpp