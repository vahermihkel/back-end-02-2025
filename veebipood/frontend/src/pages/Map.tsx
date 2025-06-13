import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet'


function Map() {
  return (
    <div>
      <MapContainer className="map" center={[59.436, 24.753]} zoom={12} scrollWheelZoom={false}>
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <Marker position={[59.427, 24.723]}>
          <Popup>
            Kristiine keskus. <br />
            <a 
              target='_blank'
              href="https://maps.app.goo.gl/Aca3Qoc62qZjWwka9">
              Endla 45, Tallinn
            </a>
          </Popup>
        </Marker>
        <Marker position={[59.421, 24.793]}>
          <Popup>
            Ülemiste keskus. <br />
            <a 
              target='_blank'
              href="https://maps.app.goo.gl/DsdAZDrz5ktGZ11w9">
              Suur-sõjamäe 45, Tallinn
            </a>
          </Popup>
        </Marker>
      </MapContainer>
    </div>
  )
}

export default Map