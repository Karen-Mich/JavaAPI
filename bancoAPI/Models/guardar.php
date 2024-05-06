<?php
class crudG{
    public static function GuardarTransaccion(){
        include_once ('conexion.php');
        header('Content-Type: application/json');
        $data = json_decode(file_get_contents("php://input"), true);
        //$id = $data["id_tra"];
        $movimiento = $data["mov_tra"];
        $monto = $data["mon_tra"];
        $fecha = $data["fec_tra"];
        $cuenta = $data["id_cue_per"];
        $objeto = new conexion();
        $conectar = $objeto -> conectar();
        $insertSql="INSERT INTO transacciones (mov_tra, mon_tra, fec_tra, id_cue_per) VALUES ('$movimiento','$monto','$fecha','$cuenta')";
        $resultado = $conectar->prepare($insertSql);
        $resultado->execute();
        echo json_encode($resultado);
        $conectar->commit();
    }
}