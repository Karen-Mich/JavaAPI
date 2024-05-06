<?php
class crudA
{
    public static function ActualizarTransaccion()
    {
        include_once ('conexion.php');
        header('Content-Type: application/json');
        $objeto = new conexion();
        $conectar = $objeto->conectar();
        //$data = json_decode(file_get_contents("php://input"), true);
        $id = $_GET["id_tra"];
        $movimiento = $_GET["mov_tra"];
        $monto = $_GET["mon_tra"];
        $fecha = $_GET["fec_tra"];
        $cuenta = $_GET["id_cue_per"];
        $actualizarSql = "UPDATE transacciones SET mov_tra='$movimiento', mon_tra='$monto', fec_tra='$fecha', id_cue_per='$cuenta' WHERE id_tra = '$id' ";
        $resultado = $conectar->prepare($actualizarSql);
        $resultado->execute();
        echo json_encode($resultado);
        $conectar->commit();
    }
}
?>