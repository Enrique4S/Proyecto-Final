const mongoose = require('mongoose');

const EntrenamientoSchema = new mongoose.Schema({
  usuarioId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Usuario'
  },
  textoOriginal: String,

  metricas: {
    distanciaKm: Number,
    duracionMin: Number,
    calorias: Number
  },

  fechaEntrenamiento: Date,
  horaRegistro: String,
  timestamp: Date
});

module.exports = mongoose.model('Entrenamiento', EntrenamientoSchema);