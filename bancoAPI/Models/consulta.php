<?php
class crudS{
    public static function SeleccionarTransaccion(){
        include_once('conexion.php');
        header('Content-Type: application/json');
        $objeto = new conexion();
        $conexion = $objeto->conectar();
        $sqlSelect = "Select * from transacciones";
        $resultado = $conexion->prepare($sqlSelect);
        $resultado->execute();
        $data=$resultado->fetchAll(PDO::FETCH_ASSOC);
        echo (json_encode($data)) ;
    }
}

?>