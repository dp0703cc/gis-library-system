const map = L.map('map').setView([30.66, 104.07], 12);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution:'&copy; OpenStreetMap contributors'
}).addTo(map);

const statusColor = {
  AVAILABLE: '#2ecc71',
  BORROWED:  '#e74c3c',
  READING_ROOM: '#f1c40f',
  ON_HOLD: '#9b59b6'
};

function markerFor(lib) {
  return L.circleMarker([lib.lat, lib.lon], {
    radius: 7, weight: 1, color: '#333',
    fillColor: statusColor[lib.status || 'AVAILABLE'] || '#3498db', fillOpacity: 0.9
  }).bindPopup(`<b>${lib.lib_name}</b><br>${lib.address || ''}`);
}

const legend = L.control({position: 'bottomright'});
legend.onAdd = function() {
  const div = L.DomUtil.create('div', 'legend');
  div.innerHTML = `
    <div><i style="background:${statusColor.AVAILABLE}"></i>可借</div>
    <div><i style="background:${statusColor.BORROWED}"></i>已借出</div>
    <div><i style="background:${statusColor.READING_ROOM}"></i>仅阅览</div>
    <div><i style="background:${statusColor.ON_HOLD}"></i>预约中</div>`;
  return div;
};
legend.addTo(map);

fetch('/api/libraries').then(r=>r.json()).then(list=>{
  list.forEach(lib => markerFor(lib).addTo(map));
});

map.on('click', e=>{
  const lat = e.latlng.lat, lon = e.latlng.lng;
  fetch(`/api/libraries/nearby?lat=${lat}&lon=${lon}&radiusKm=2`)
    .then(r=>r.json())
    .then(list=>{
      list.forEach(lib=> markerFor(lib).addTo(map).openPopup());
      alert(`2公里内共有 ${list.length} 个图书馆`);
    });
});

document.getElementById('btnSearch').addEventListener('click', ()=>{
  const kw = document.getElementById('q').value || '';
  fetch(`/api/books/search?q=${encodeURIComponent(kw)}`)
    .then(r=>r.json())
    .then(rows=>{
      const tbody = document.querySelector('#result tbody');
      tbody.innerHTML='';
      rows.forEach(r=>{
        const tr = document.createElement('tr');
        tr.innerHTML = `<td>${r.isbn}</td><td>${r.title}</td><td>${r.author||''}</td>
                        <td>${r.inventory_id}</td><td>${r.status}</td><td>${r.lib_name}</td>`;
        tbody.appendChild(tr);
      });
    });
});
