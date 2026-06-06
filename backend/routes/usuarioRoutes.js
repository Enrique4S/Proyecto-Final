const express = require('express');
const router = express.Router();
const Usuario = require('../models/Usuario'); // 👈 CORREGIDO

// GET todos los usuarios
router.get('/usuarios', async (req, res) => {
  const usuarios = await Usuario.find();
  res.json(usuarios);
});

// POST crear usuario
router.post('/usuarios', async (req, res) => {
  const nuevoUsuario = new Usuario(req.body);
  await nuevoUsuario.save();
  res.json(nuevoUsuario);
});

module.exports = router;