const mongoose = require('mongoose');

const UsuarioSchema = new mongoose.Schema({
  nombre: String,
  correo: String,
  rol: String,
  fechaRegistro: Date,
  activo: Boolean
});

module.exports = mongoose.model('Usuario', UsuarioSchema);