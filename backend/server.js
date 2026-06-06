const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const connectDB = require('./db');

dotenv.config();

const app = express();

// Middleware
app.use(cors());
app.use(express.json());

// Conexión a MongoDB
connectDB();

// Rutas
const usuarioRoutes = require('./routes/usuarioRoutes');
app.use('/api', usuarioRoutes);

const entrenamientoRoutes = require('./routes/entrenamientoRoutes');

app.use('/api/entrenamientos', entrenamientoRoutes);

// Ruta de prueba
app.get('/', (req, res) => {
  res.send('API funcionando 🚀');
});

// Puerto
const PORT = process.env.PORT || 3000;

// Iniciar servidor
app.listen(PORT, () => {
  console.log(`Servidor corriendo en puerto ${PORT}`);
});