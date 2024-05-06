<?php
class crudB{
    public static function BorrarTransaccion($id){
        include_once('conexion.php');
        $objeto = new conexion();
        $conectar = $objeto ->conectar();
        $id=$_GET["id_tra"];
        $borrarSql = "DELETE FROM transacciones WHERE id_tra='$id'";
        $resultado = $conectar -> prepare($borrarSql);
        $resultado->execute();
        echo json_encode($resultado);
        $conectar->commit();
    }
}

?>