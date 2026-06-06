const express = require('express');
const router = express.Router();
const Entrenamiento = require('../models/Entrenamiento');

// GET todos los entrenamientos
router.get('/', async (req, res) => {
  const data = await Entrenamiento.find();
  res.json(data);
});

// GET por usuario
router.get('/usuario/:id', async (req, res) => {
  const data = await Entrenamiento.find({ usuarioId: req.params.id });
  res.json(data);
});

// POST nuevo entrenamiento
router.post('/', async (req, res) => {
  const nuevo = new Entrenamiento(req.body);
  await nuevo.save();
  res.json(nuevo);
});

module.exports = router;