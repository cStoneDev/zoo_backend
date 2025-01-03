PGDMP      )                |            zoo    16.4    16.4               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16398    zoo    DATABASE     u   CREATE DATABASE zoo WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Cuba.1252';
    DROP DATABASE zoo;
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false                       0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    5            4           1255    16399    actividad_delete(integer)    FUNCTION     �   CREATE FUNCTION public.actividad_delete(id_actividad_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "actividad"
   where "id_actividad"= $1; 
 END;$_$;
 ?   DROP FUNCTION public.actividad_delete(id_actividad_0 integer);
       public          postgres    false    5            L           1255    16400 =   actividad_insert(date, time without time zone, integer, text)    FUNCTION     Q  CREATE FUNCTION public.actividad_insert(fecha_1 date, hora_2 time without time zone, id_contrato_3 integer, descripcion_actividad_4 text) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into actividad(fecha,hora,id_contrato,descripcion_actividad)values("fecha_1","hora_2","id_contrato_3","descripcion_actividad_4"); 
  END;$$;
 �   DROP FUNCTION public.actividad_insert(fecha_1 date, hora_2 time without time zone, id_contrato_3 integer, descripcion_actividad_4 text);
       public          postgres    false    5            m           1255    16401 F   actividad_update(integer, date, time without time zone, integer, text)    FUNCTION     L  CREATE FUNCTION public.actividad_update(id_actividad_0 integer, fecha_1 date, hora_2 time without time zone, id_contrato_3 integer, descripcion_actividad_4 text) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "actividad"
 SET fecha=$2,hora=$3,id_contrato=$4,descripcion_actividad=$5 where "id_actividad"= $1; 
 END;$_$;
 �   DROP FUNCTION public.actividad_update(id_actividad_0 integer, fecha_1 date, hora_2 time without time zone, id_contrato_3 integer, descripcion_actividad_4 text);
       public          postgres    false    5            k           1255    16402    actividadesactivas()    FUNCTION        CREATE FUNCTION public.actividadesactivas() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
Declare 
 resp refcursor;
BEGIN
 Open resp for
 Select * from actividad where (fecha > current_date
 OR (fecha = current_date AND hora >= current_time(0)::time));
 return resp;
END;
$$;
 +   DROP FUNCTION public.actividadesactivas();
       public          postgres    false    5                       1255    16403 $   actividadesactivas_byanimal(integer)    FUNCTION     �  CREATE FUNCTION public.actividadesactivas_byanimal(id_animal integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare 
 resp refcursor;
BEGIN
 Open resp for
 Select actividad.* from programacion Inner Join actividad using(id_actividad)
 Where programacion.id_animal = $1 AND id_actividad IN
 (Select id_actividad from actividad where (fecha > current_date
 OR (fecha = current_date AND hora >= current_time(0)::time)));
 return resp;
END;
$_$;
 E   DROP FUNCTION public.actividadesactivas_byanimal(id_animal integer);
       public          postgres    false    5                       1255    16404    actividadesenadopcion()    FUNCTION     %  CREATE FUNCTION public.actividadesenadopcion() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	DELETE FROM programacion
	WHERE id_animal = NEW.id_animal 
	AND id_actividad IN (
		SELECT id_actividad 
		FROM actividad 
		WHERE fecha >= CURRENT_DATE
	);

	return NEW;
END;
$$;
 .   DROP FUNCTION public.actividadesenadopcion();
       public          postgres    false    5            ,           1255    16405    actividadesnoactivas()    FUNCTION     !  CREATE FUNCTION public.actividadesnoactivas() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
Declare 
 resp refcursor;
BEGIN
 Open resp for
 Select * from actividad where (fecha < current_date
 OR (fecha = current_date AND hora < current_time(0)::time));
 return resp;
END;
$$;
 -   DROP FUNCTION public.actividadesnoactivas();
       public          postgres    false    5            "           1255    16406 &   actividadesnoactivas_byanimal(integer)    FUNCTION     �  CREATE FUNCTION public.actividadesnoactivas_byanimal(id_animal integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare 
 resp refcursor;
BEGIN
 Open resp for
 Select actividad.* from programacion Inner Join actividad using(id_actividad)
 Where programacion.id_animal = $1 AND id_actividad IN
 (Select id_actividad from actividad where (fecha < current_date
 OR (fecha = current_date AND hora < current_time(0)::time)));
 return resp;
END;
$_$;
 G   DROP FUNCTION public.actividadesnoactivas_byanimal(id_animal integer);
       public          postgres    false    5            b           1255    16407    adopcion_delete(integer)    FUNCTION     �   CREATE FUNCTION public.adopcion_delete(id_adopcion_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "adopcion"
   where "id_adopcion"= $1; 
 END;$_$;
 =   DROP FUNCTION public.adopcion_delete(id_adopcion_0 integer);
       public          postgres    false    5            �            1255    16408 !   adopcion_insert(integer, numeric)    FUNCTION     �   CREATE FUNCTION public.adopcion_insert(id_animal_1 integer, precio_adopcion_2 numeric) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into adopcion(id_animal,precio_adopcion)values("id_animal_1","precio_adopcion_2"); 
  END;$$;
 V   DROP FUNCTION public.adopcion_insert(id_animal_1 integer, precio_adopcion_2 numeric);
       public          postgres    false    5            8           1255    16409 *   adopcion_update(integer, integer, numeric)    FUNCTION     �   CREATE FUNCTION public.adopcion_update(id_adopcion_0 integer, id_animal_1 integer, precio_adopcion_2 numeric) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "adopcion"
 SET id_animal=$2,precio_adopcion=$3 where "id_adopcion"= $1; 
 END;$_$;
 m   DROP FUNCTION public.adopcion_update(id_adopcion_0 integer, id_animal_1 integer, precio_adopcion_2 numeric);
       public          postgres    false    5            M           1255    16410    animal_delete(integer)    FUNCTION     �   CREATE FUNCTION public.animal_delete(id_animal_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "animal"
   where "id_animal"= $1; 
 END;$_$;
 9   DROP FUNCTION public.animal_delete(id_animal_0 integer);
       public          postgres    false    5                        1255    16411 J   animal_insert(character varying, integer, integer, double precision, date)    FUNCTION     Q  CREATE FUNCTION public.animal_insert(nombre_1 character varying, id_raza_2 integer, edad_3 integer, peso_4 double precision, fecha_ingreso_5 date) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into animal(nombre,id_raza,edad,peso,fecha_ingreso)values("nombre_1","id_raza_2","edad_3","peso_4","fecha_ingreso_5"); 
  END;$$;
 �   DROP FUNCTION public.animal_insert(nombre_1 character varying, id_raza_2 integer, edad_3 integer, peso_4 double precision, fecha_ingreso_5 date);
       public          postgres    false    5                       1255    16412 S   animal_update(integer, character varying, integer, integer, double precision, date)    FUNCTION     L  CREATE FUNCTION public.animal_update(id_animal_0 integer, nombre_1 character varying, id_raza_2 integer, edad_3 integer, peso_4 double precision, fecha_ingreso_5 date) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "animal"
 SET nombre=$2,id_raza=$3,edad=$4,peso=$5,fecha_ingreso=$6 where "id_animal"= $1; 
 END;$_$;
 �   DROP FUNCTION public.animal_update(id_animal_0 integer, nombre_1 character varying, id_raza_2 integer, edad_3 integer, peso_4 double precision, fecha_ingreso_5 date);
       public          postgres    false    5            $           1255    16413    animalesactivos()    FUNCTION     �  CREATE FUNCTION public.animalesactivos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
Declare 
	resp refcursor;
BEGIN 
	Open resp for 
	Select animal.id_animal, animal.nombre, animal.id_raza, animal.edad, animal.peso, CURRENT_DATE - animal.fecha_ingreso AS dias_refugio from animal Left Join adopcion using(id_animal)
	Where id_adopcion is null;
	return resp;
	
END;

$$;
 (   DROP FUNCTION public.animalesactivos();
       public          postgres    false    5                       1255    16414    animalesnoactivos()    FUNCTION     �  CREATE FUNCTION public.animalesnoactivos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
Declare 
	resp refcursor;
BEGIN 
	Open resp for 
	Select animal.id_animal, animal.nombre, animal.id_raza, animal.edad, animal.peso, CURRENT_DATE - animal.fecha_ingreso AS dias_refugio from animal Left Join adopcion using(id_animal)
	Where id_adopcion is not null;
	return resp;
	
END;
$$;
 *   DROP FUNCTION public.animalesnoactivos();
       public          postgres    false    5            1           1255    16415    cantidadtopespecies()    FUNCTION     �  CREATE FUNCTION public.cantidadtopespecies() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
    ref refcursor;
BEGIN
    OPEN ref FOR
    SELECT especie.nombre, COUNT(animal.id_raza) AS cantidad
    FROM animal
    JOIN raza ON animal.id_raza = raza.id_raza
    JOIN especie ON raza.id_especie = especie.id_especie
    GROUP BY especie.nombre
    ORDER BY cantidad DESC
    LIMIT 5;

    RETURN ref;
END;
$$;
 ,   DROP FUNCTION public.cantidadtopespecies();
       public          postgres    false    5            S           1255    16416    clinica_delete(integer)    FUNCTION     �   CREATE FUNCTION public.clinica_delete(id_clinica_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "clinica"
   where "id_clinica"= $1; 
 END;$_$;
 ;   DROP FUNCTION public.clinica_delete(id_clinica_0 integer);
       public          postgres    false    5            �            1255    16417 !   clinica_insert(character varying)    FUNCTION     �   CREATE FUNCTION public.clinica_insert(nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into clinica(nombre)values("nombre_1"); 
  END;$$;
 A   DROP FUNCTION public.clinica_insert(nombre_1 character varying);
       public          postgres    false    5                       1255    16418 *   clinica_update(integer, character varying)    FUNCTION     �   CREATE FUNCTION public.clinica_update(id_clinica_0 integer, nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "clinica"
 SET nombre=$2 where "id_clinica"= $1; 
 END;$_$;
 W   DROP FUNCTION public.clinica_update(id_clinica_0 integer, nombre_1 character varying);
       public          postgres    false    5            N           1255    16419 5   confirmar_login(character varying, character varying)    FUNCTION       CREATE FUNCTION public.confirmar_login(nombre character varying, contrasena character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
    is_valid boolean;
BEGIN
    -- Verificar si existe un usuario con las credenciales proporcionadas
    SELECT EXISTS (
        SELECT 1
        FROM usuario
        WHERE nombre_usuario = nombre
          AND clave = md5(contrasena || 'salt1')
    ) INTO is_valid;

    -- Devolver true si el usuario existe, de lo contrario false
    RETURN is_valid;
END;
$$;
 ^   DROP FUNCTION public.confirmar_login(nombre character varying, contrasena character varying);
       public          postgres    false    5            T           1255    16420    contrato_delete(integer)    FUNCTION     �   CREATE FUNCTION public.contrato_delete(id_contrato_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "contrato"
   where "id_contrato"= $1; 
 END;$_$;
 =   DROP FUNCTION public.contrato_delete(id_contrato_0 integer);
       public          postgres    false    5            G           1255    16421 B   contrato_insert(integer, date, date, date, text, numeric, numeric)    FUNCTION     �  CREATE FUNCTION public.contrato_insert(id_proveedor_1 integer, fecha_inicio_2 date, fecha_terminacion_3 date, fecha_conciliacion_4 date, descripcion_5 text, precio_base_6 numeric, recargo_7 numeric) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into contrato(id_proveedor,fecha_inicio,fecha_terminacion,fecha_conciliacion,descripcion,precio_base,recargo)values("id_proveedor_1","fecha_inicio_2","fecha_terminacion_3","fecha_conciliacion_4","descripcion_5","precio_base_6","recargo_7"); 
  END;$$;
 �   DROP FUNCTION public.contrato_insert(id_proveedor_1 integer, fecha_inicio_2 date, fecha_terminacion_3 date, fecha_conciliacion_4 date, descripcion_5 text, precio_base_6 numeric, recargo_7 numeric);
       public          postgres    false    5                       1255    16422 K   contrato_update(integer, integer, date, date, date, text, numeric, numeric)    FUNCTION     �  CREATE FUNCTION public.contrato_update(id_contrato_0 integer, id_proveedor_1 integer, fecha_inicio_2 date, fecha_terminacion_3 date, fecha_conciliacion_4 date, descripcion_5 text, precio_base_6 numeric, recargo_7 numeric) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "contrato"
 SET id_proveedor=$2,fecha_inicio=$3,fecha_terminacion=$4,fecha_conciliacion=$5,descripcion=$6,precio_base=$7,recargo=$8 where "id_contrato"= $1; 
 END;$_$;
 �   DROP FUNCTION public.contrato_update(id_contrato_0 integer, id_proveedor_1 integer, fecha_inicio_2 date, fecha_terminacion_3 date, fecha_conciliacion_4 date, descripcion_5 text, precio_base_6 numeric, recargo_7 numeric);
       public          postgres    false    5                       1255    16423    contratosactivos()    FUNCTION       CREATE FUNCTION public.contratosactivos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
Declare
	resp refcursor;
Begin
	Open resp for
	Select * from contrato 
	Where (fecha_terminacion >= current_date AND fecha_inicio <= current_date);
	return resp;
END;
$$;
 )   DROP FUNCTION public.contratosactivos();
       public          postgres    false    5                       1255    16424    contratosfuturos()    FUNCTION     �   CREATE FUNCTION public.contratosfuturos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
Declare
	resp refcursor;
Begin
	Open resp for
	Select * from contrato 
	Where (fecha_inicio > current_date);
	return resp;
END;
$$;
 )   DROP FUNCTION public.contratosfuturos();
       public          postgres    false    5            H           1255    16425    contratosnoactivos()    FUNCTION     �   CREATE FUNCTION public.contratosnoactivos() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
Declare
	resp refcursor;
Begin
	Open resp for
	Select * from contrato 
	Where (fecha_terminacion < current_date);
	return resp;
END;
$$;
 +   DROP FUNCTION public.contratosnoactivos();
       public          postgres    false    5            O           1255    16426    diasrefugio(integer)    FUNCTION     :  CREATE FUNCTION public.diasrefugio(animal_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    entry_date DATE;
    current_date DATE;
    days_in_shelter INT;
BEGIN
    -- Obtener la fecha de entrada del animal
    SELECT fecha_ingreso INTO entry_date 
    FROM animal
    WHERE id_animal = diasRefugio.animal_id;
    
    -- Obtener la fecha actual del sistema
    current_date := CURRENT_DATE;
    
    -- Calcular la diferencia en días
    days_in_shelter := current_date - entry_date;
    
    RETURN days_in_shelter;
END;
$$;
 5   DROP FUNCTION public.diasrefugio(animal_id integer);
       public          postgres    false    5            `           1255    16427    donacion_delete(integer)    FUNCTION     �   CREATE FUNCTION public.donacion_delete(id_donacion_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "donacion"
   where "id_donacion"= $1; 
 END;$_$;
 =   DROP FUNCTION public.donacion_delete(id_donacion_0 integer);
       public          postgres    false    5            
           1255    16428 '   donacion_insert(integer, numeric, date)    FUNCTION     *  CREATE FUNCTION public.donacion_insert(id_animal_1 integer, precio_donacion_2 numeric, fecha_donacion_3 date) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into donacion(id_animal,precio_donacion,fecha_donacion)values("id_animal_1","precio_donacion_2","fecha_donacion_3"); 
  END;$$;
 m   DROP FUNCTION public.donacion_insert(id_animal_1 integer, precio_donacion_2 numeric, fecha_donacion_3 date);
       public          postgres    false    5            0           1255    16429 0   donacion_update(integer, integer, numeric, date)    FUNCTION     )  CREATE FUNCTION public.donacion_update(id_donacion_0 integer, id_animal_1 integer, precio_donacion_2 numeric, fecha_donacion_3 date) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "donacion"
 SET id_animal=$2,precio_donacion=$3,fecha_donacion=$4 where "id_donacion"= $1; 
 END;$_$;
 �   DROP FUNCTION public.donacion_update(id_donacion_0 integer, id_animal_1 integer, precio_donacion_2 numeric, fecha_donacion_3 date);
       public          postgres    false    5            3           1255    16430    especialidad_delete(integer)    FUNCTION     �   CREATE FUNCTION public.especialidad_delete(id_especialidad_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "especialidad"
   where "id_especialidad"= $1; 
 END;$_$;
 E   DROP FUNCTION public.especialidad_delete(id_especialidad_0 integer);
       public          postgres    false    5            E           1255    16431 &   especialidad_insert(character varying)    FUNCTION     �   CREATE FUNCTION public.especialidad_insert(nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into especialidad(nombre)values("nombre_1"); 
  END;$$;
 F   DROP FUNCTION public.especialidad_insert(nombre_1 character varying);
       public          postgres    false    5            ^           1255    16432 /   especialidad_update(integer, character varying)    FUNCTION     �   CREATE FUNCTION public.especialidad_update(id_especialidad_0 integer, nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "especialidad"
 SET nombre=$2 where "id_especialidad"= $1; 
 END;$_$;
 a   DROP FUNCTION public.especialidad_update(id_especialidad_0 integer, nombre_1 character varying);
       public          postgres    false    5            B           1255    16433    especie_delete(integer)    FUNCTION     �   CREATE FUNCTION public.especie_delete(id_especie_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "especie"
   where "id_especie"= $1; 
 END;$_$;
 ;   DROP FUNCTION public.especie_delete(id_especie_0 integer);
       public          postgres    false    5                       1255    16434 !   especie_insert(character varying)    FUNCTION     �   CREATE FUNCTION public.especie_insert(nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into especie(nombre)values("nombre_1"); 
  END;$$;
 A   DROP FUNCTION public.especie_insert(nombre_1 character varying);
       public          postgres    false    5            =           1255    16435 *   especie_update(integer, character varying)    FUNCTION     �   CREATE FUNCTION public.especie_update(id_especie_0 integer, nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "especie"
 SET nombre=$2 where "id_especie"= $1; 
 END;$_$;
 W   DROP FUNCTION public.especie_update(id_especie_0 integer, nombre_1 character varying);
       public          postgres    false    5            a           1255    16436    fechaactividad()    FUNCTION     �  CREATE FUNCTION public.fechaactividad() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
Declare
	fechaNueva date;
	fechaInic date;
	fechaFinal date;
	horaNueva time without time zone;
BEGIN
	fechaNueva = New.fecha;
	horaNueva = New.hora;

	Select Distinct Into fechaInic contrato.fecha_inicio
	from contrato inner join actividad using(id_contrato)
	where contrato.id_contrato = New.id_contrato;

	Select Distinct Into fechaFinal contrato.fecha_terminacion
	from contrato inner join actividad using(id_contrato)
	where contrato.id_contrato = New.id_contrato;

	IF (TG_OP = 'INSERT' OR (TG_OP = 'UPDATE' AND (NEW.fecha != OLD.fecha OR NEW.hora != OLD.hora))) THEN
	IF NOT (fechaNueva BETWEEN fechaInic AND fechaFinal) THEN
		Raise Exception 'La fecha de la Actividad no corresponde con la fecha del contrato asignado';
	ELSIF (fechaNueva < current_date ) THEN
		Raise Exception 'La fecha de la Actividad se intentó poner atrasada';
	ELSIF (fechaNueva = current_date AND horaNueva < current_time(0)::time without time zone) THEN
		Raise Exception 'La fecha de la Actividad se intentó poner atrasada (la hora esta atrasada)';
	END IF;
	END IF;
	RETURN NEW;
END;
$$;
 '   DROP FUNCTION public.fechaactividad();
       public          postgres    false    5            C           1255    16437    fechadonacion()    FUNCTION     �  CREATE FUNCTION public.fechadonacion() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	fechaIngreso date;
BEGIN

	Select into fechaIngreso animal.fecha_ingreso from animal where animal.id_animal = NEW.id_animal; 
	
	IF NEW.fecha_donacion < fechaIngreso THEN
		RAISE EXCEPTION 'La fecha de donacion no puede ser inferior a la de ingreso';
	END IF;
	return NEW;
END;
$$;
 &   DROP FUNCTION public.fechadonacion();
       public          postgres    false    5            )           1255    16438    find_actividad(integer)    FUNCTION     �   CREATE FUNCTION public.find_actividad(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from actividad
	where id_actividad= $1;
	return respuesta;
End

$_$;
 .   DROP FUNCTION public.find_actividad(integer);
       public          postgres    false    5            P           1255    16439    find_adopcion(integer)    FUNCTION     �   CREATE FUNCTION public.find_adopcion(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from adopcion
	where id_adopcion= $1;
	return respuesta;
End

$_$;
 -   DROP FUNCTION public.find_adopcion(integer);
       public          postgres    false    5            �            1255    16440 !   find_adopcionbyid_animal(integer)    FUNCTION     �   CREATE FUNCTION public.find_adopcionbyid_animal(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from adopcion
	where id_animal= $1;
	return respuesta;
End

$_$;
 8   DROP FUNCTION public.find_adopcionbyid_animal(integer);
       public          postgres    false    5                       1255    16441    find_animal(integer)    FUNCTION     �  CREATE FUNCTION public.find_animal(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	SELECT 
        a.id_animal,
        a.nombre,
        a.id_raza,
        a.edad,
        a.peso,
        CURRENT_DATE - a.fecha_ingreso AS dias_refugio
	FROM 
        animal a
	where id_animal= $1;
	return respuesta;
End

$_$;
 +   DROP FUNCTION public.find_animal(integer);
       public          postgres    false    5                       1255    16442    find_clinica(integer)    FUNCTION     �   CREATE FUNCTION public.find_clinica(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from clinica
	where id_clinica= $1;
	return respuesta;
End

$_$;
 ,   DROP FUNCTION public.find_clinica(integer);
       public          postgres    false    5                       1255    16443    find_contrato(integer)    FUNCTION     �   CREATE FUNCTION public.find_contrato(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from contrato
	where id_contrato= $1;
	return respuesta;
End

$_$;
 -   DROP FUNCTION public.find_contrato(integer);
       public          postgres    false    5            D           1255    16444 $   find_contratobyid_proveedor(integer)    FUNCTION     �   CREATE FUNCTION public.find_contratobyid_proveedor(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from contrato
	where id_proveedor= $1;
	return respuesta;
End

$_$;
 ;   DROP FUNCTION public.find_contratobyid_proveedor(integer);
       public          postgres    false    5            R           1255    16445    find_donacion(integer)    FUNCTION     �   CREATE FUNCTION public.find_donacion(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from donacion
	where id_donacion= $1;
	return respuesta;
End

$_$;
 -   DROP FUNCTION public.find_donacion(integer);
       public          postgres    false    5            c           1255    16446    find_especialidad(integer)    FUNCTION     �   CREATE FUNCTION public.find_especialidad(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from especialidad
	where id_especialidad= $1;
	return respuesta;
End

$_$;
 1   DROP FUNCTION public.find_especialidad(integer);
       public          postgres    false    5                       1255    16447    find_especie(integer)    FUNCTION     �   CREATE FUNCTION public.find_especie(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from especie
	where id_especie= $1;
	return respuesta;
End

$_$;
 ,   DROP FUNCTION public.find_especie(integer);
       public          postgres    false    5            @           1255    16448    find_programacion(integer)    FUNCTION     �   CREATE FUNCTION public.find_programacion(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from programacion
	where id_programacion= $1;
	return respuesta;
End

$_$;
 1   DROP FUNCTION public.find_programacion(integer);
       public          postgres    false    5            r           1255    16449    find_proveedor(integer)    FUNCTION     �   CREATE FUNCTION public.find_proveedor(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from proveedor
	where id_proveedor= $1;
	return respuesta;
End

$_$;
 .   DROP FUNCTION public.find_proveedor(integer);
       public          postgres    false    5            n           1255    16450    find_provincia(integer)    FUNCTION     �   CREATE FUNCTION public.find_provincia(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from provincia
	where id_provincia= $1;
	return respuesta;
End

$_$;
 .   DROP FUNCTION public.find_provincia(integer);
       public          postgres    false    5                       1255    16451    find_raza(integer)    FUNCTION     �   CREATE FUNCTION public.find_raza(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from raza
	where id_raza= $1;
	return respuesta;
End

$_$;
 )   DROP FUNCTION public.find_raza(integer);
       public          postgres    false    5            9           1255    16452    find_rol(integer)    FUNCTION     �   CREATE FUNCTION public.find_rol(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from rol
	where id_rol= $1;
	return respuesta;
End

$_$;
 (   DROP FUNCTION public.find_rol(integer);
       public          postgres    false    5                       1255    16453    find_tipoproveedor(integer)    FUNCTION     �   CREATE FUNCTION public.find_tipoproveedor(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from tipoproveedor
	where id_tipo_proveedor= $1;
	return respuesta;
End

$_$;
 2   DROP FUNCTION public.find_tipoproveedor(integer);
       public          postgres    false    5                       1255    16454    find_tiposervicio(integer)    FUNCTION     �   CREATE FUNCTION public.find_tiposervicio(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from tiposervicio
	where id_tipo_servicio= $1;
	return respuesta;
End

$_$;
 1   DROP FUNCTION public.find_tiposervicio(integer);
       public          postgres    false    5            J           1255    16455    find_usuario(integer)    FUNCTION     �   CREATE FUNCTION public.find_usuario(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from usuario
	where id_usuario= $1;
	return respuesta;
End

$_$;
 ,   DROP FUNCTION public.find_usuario(integer);
       public          postgres    false    5                       1255    16456    find_usuario(character varying)    FUNCTION       CREATE FUNCTION public.find_usuario(nombre_user character varying) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
    ref refcursor;
BEGIN
    OPEN ref FOR
    SELECT * FROM usuario WHERE nombre_usuario = nombre_user;
    RETURN ref;
END;
$$;
 B   DROP FUNCTION public.find_usuario(nombre_user character varying);
       public          postgres    false    5            p           1255    16457    find_veterinario(integer)    FUNCTION     �   CREATE FUNCTION public.find_veterinario(integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $_$
Declare
	respuesta refcursor;
Begin
	open respuesta for
	select * from veterinario
	where id_proveedor = $1;
	return respuesta;
End

$_$;
 0   DROP FUNCTION public.find_veterinario(integer);
       public          postgres    false    5                       1255    16458    get_donaciones_por_mes(integer)    FUNCTION     c  CREATE FUNCTION public.get_donaciones_por_mes(anio integer) RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
    ref_cursor REFCURSOR;
BEGIN
    -- Abrir el cursor
    OPEN ref_cursor FOR
    SELECT 
        TO_CHAR(DATE_TRUNC('month', fecha_donacion), 'FMMonth') AS mes,
        COUNT(*) AS cantidad_donaciones
    FROM 
        public.donacion
    WHERE 
        EXTRACT(YEAR FROM fecha_donacion) = anio
    GROUP BY 
        DATE_TRUNC('month', fecha_donacion)
    ORDER BY 
        DATE_TRUNC('month', fecha_donacion);

    -- Retornar el cursor
    RETURN ref_cursor;
END;
$$;
 ;   DROP FUNCTION public.get_donaciones_por_mes(anio integer);
       public          postgres    false    5            /           1255    16459    precioadopcion()    FUNCTION     �   CREATE FUNCTION public.precioadopcion() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF NEW.precio_adopcion < 50 THEN
		RAISE EXCEPTION 'EL precio de la adopcion no puede ser menor que 50';
	END IF;
	return NEW;
END;
$$;
 '   DROP FUNCTION public.precioadopcion();
       public          postgres    false    5            _           1255    16460    programacion_delete(integer)    FUNCTION     �   CREATE FUNCTION public.programacion_delete(id_programacion_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "programacion"
   where "id_programacion"= $1; 
 END;$_$;
 E   DROP FUNCTION public.programacion_delete(id_programacion_0 integer);
       public          postgres    false    5            Y           1255    16461 %   programacion_insert(integer, integer)    FUNCTION     �   CREATE FUNCTION public.programacion_insert(id_actividad_1 integer, id_animal_2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into programacion(id_actividad,id_animal)values("id_actividad_1","id_animal_2"); 
  END;$$;
 W   DROP FUNCTION public.programacion_insert(id_actividad_1 integer, id_animal_2 integer);
       public          postgres    false    5            [           1255    16462 .   programacion_update(integer, integer, integer)    FUNCTION       CREATE FUNCTION public.programacion_update(id_programacion_0 integer, id_actividad_1 integer, id_animal_2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "programacion"
 SET id_actividad=$2,id_animal=$3 where "id_programacion"= $1; 
 END;$_$;
 r   DROP FUNCTION public.programacion_update(id_programacion_0 integer, id_actividad_1 integer, id_animal_2 integer);
       public          postgres    false    5            '           1255    16463    programacionactiva()    FUNCTION     �  CREATE FUNCTION public.programacionactiva() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listprogramacion refcursor;
BEGIN
       OPEN listprogramacion FOR 
		 Select * from programacion 
		 Where id_actividad IN (Select id_actividad from actividad where (fecha > current_date
		 OR (fecha = current_date AND hora >= current_time(0)::time)))
		 order by programacion.id_programacion ;
	    RETURN listprogramacion;
		END;
       $$;
 +   DROP FUNCTION public.programacionactiva();
       public          postgres    false    5            W           1255    16464    programacionnoactiva()    FUNCTION     �  CREATE FUNCTION public.programacionnoactiva() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listprogramacion refcursor;
BEGIN
       OPEN listprogramacion FOR 
		 Select * from programacion 
		 Where id_actividad IN (Select id_actividad from actividad where (fecha < current_date
		 OR (fecha = current_date AND hora <= current_time(0)::time)))
		 order by programacion.id_programacion ;
	    RETURN listprogramacion;
		END;
       $$;
 -   DROP FUNCTION public.programacionnoactiva();
       public          postgres    false    5            (           1255    16465    proveedor_delete(integer)    FUNCTION     �   CREATE FUNCTION public.proveedor_delete(id_proveedor_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "proveedor"
   where "id_proveedor"= $1; 
 END;$_$;
 ?   DROP FUNCTION public.proveedor_delete(id_proveedor_0 integer);
       public          postgres    false    5                       1255    16466 �   proveedor_insert(character varying, integer, integer, integer, character varying, character varying, character varying, character varying)    FUNCTION     X  CREATE FUNCTION public.proveedor_insert(nombre_1 character varying, id_provincia_2 integer, id_tipo_servicio_3 integer, id_tipo_proveedor_4 integer, direccion_5 character varying, telefono_6 character varying, email_7 character varying, nombre_responsable_8 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into proveedor(nombre,id_provincia,id_tipo_servicio,id_tipo_proveedor,direccion,telefono,email,nombre_responsable)values("nombre_1","id_provincia_2","id_tipo_servicio_3","id_tipo_proveedor_4","direccion_5","telefono_6","email_7","nombre_responsable_8"); 
  END;$$;
   DROP FUNCTION public.proveedor_insert(nombre_1 character varying, id_provincia_2 integer, id_tipo_servicio_3 integer, id_tipo_proveedor_4 integer, direccion_5 character varying, telefono_6 character varying, email_7 character varying, nombre_responsable_8 character varying);
       public          postgres    false    5            &           1255    16467 �   proveedor_update(integer, character varying, integer, integer, integer, character varying, character varying, character varying, character varying)    FUNCTION       CREATE FUNCTION public.proveedor_update(id_proveedor_0 integer, nombre_1 character varying, id_provincia_2 integer, id_tipo_servicio_3 integer, id_tipo_proveedor_4 integer, direccion_5 character varying, telefono_6 character varying, email_7 character varying, nombre_responsable_8 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "proveedor"
 SET nombre=$2,id_provincia=$3,id_tipo_servicio=$4,id_tipo_proveedor=$5,direccion=$6,telefono=$7,email=$8,nombre_responsable=$9 where "id_proveedor"= $1; 
 END;$_$;
 ,  DROP FUNCTION public.proveedor_update(id_proveedor_0 integer, nombre_1 character varying, id_provincia_2 integer, id_tipo_servicio_3 integer, id_tipo_proveedor_4 integer, direccion_5 character varying, telefono_6 character varying, email_7 character varying, nombre_responsable_8 character varying);
       public          postgres    false    5            V           1255    16468    provincia_delete(integer)    FUNCTION     �   CREATE FUNCTION public.provincia_delete(id_provincia_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "provincia"
   where "id_provincia"= $1; 
 END;$_$;
 ?   DROP FUNCTION public.provincia_delete(id_provincia_0 integer);
       public          postgres    false    5            Z           1255    16469 #   provincia_insert(character varying)    FUNCTION     �   CREATE FUNCTION public.provincia_insert(nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into provincia(nombre)values("nombre_1"); 
  END;$$;
 C   DROP FUNCTION public.provincia_insert(nombre_1 character varying);
       public          postgres    false    5            	           1255    16470 ,   provincia_update(integer, character varying)    FUNCTION     �   CREATE FUNCTION public.provincia_update(id_provincia_0 integer, nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "provincia"
 SET nombre=$2 where "id_provincia"= $1; 
 END;$_$;
 [   DROP FUNCTION public.provincia_update(id_provincia_0 integer, nombre_1 character varying);
       public          postgres    false    5            �            1255    16471    raza_delete(integer)    FUNCTION     �   CREATE FUNCTION public.raza_delete(id_raza_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "raza"
   where "id_raza"= $1; 
 END;$_$;
 5   DROP FUNCTION public.raza_delete(id_raza_0 integer);
       public          postgres    false    5            h           1255    16472 '   raza_insert(character varying, integer)    FUNCTION     �   CREATE FUNCTION public.raza_insert(nombre_1 character varying, id_especie_2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into raza(nombre,id_especie)values("nombre_1","id_especie_2"); 
  END;$$;
 T   DROP FUNCTION public.raza_insert(nombre_1 character varying, id_especie_2 integer);
       public          postgres    false    5            ]           1255    16473 0   raza_update(integer, character varying, integer)    FUNCTION     �   CREATE FUNCTION public.raza_update(id_raza_0 integer, nombre_1 character varying, id_especie_2 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "raza"
 SET nombre=$2,id_especie=$3 where "id_raza"= $1; 
 END;$_$;
 g   DROP FUNCTION public.raza_update(id_raza_0 integer, nombre_1 character varying, id_especie_2 integer);
       public          postgres    false    5            e           1255    16474    revisarfechacontrato()    FUNCTION     �  CREATE FUNCTION public.revisarfechacontrato() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF( New.fecha_inicio >= New.fecha_terminacion ) THEN
		Raise Exception 'La fecha de inicio no puede ser igual o mayor a la fecha de terminación';
	ELSIF (New.fecha_conciliacion > New.fecha_inicio) THEN
		Raise Exception 'La fecha de conciliación no puede ser mayor que la fecha de inicio';
	END IF;
	Return NEW;
END;
$$;
 -   DROP FUNCTION public.revisarfechacontrato();
       public          postgres    false    5                       1255    16475    revisarfechacontratoinsert()    FUNCTION     ;  CREATE FUNCTION public.revisarfechacontratoinsert() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF( New.fecha_inicio >= New.fecha_terminacion ) THEN
		Raise Exception 'La fecha de inicio no puede ser igual o mayor a la fecha de terminación';
	ELSIF (New.fecha_conciliacion > New.fecha_inicio) THEN
		Raise Exception 'La fecha de conciliación no puede ser mayor que la fecha de inicio';
	ELSIF (New.fecha_inicio < current_date) THEN
		Raise Exception 'La fecha de inicio no se permite poner antes de la fecha actual';
	END IF;
	Return NEW;
END;
$$;
 3   DROP FUNCTION public.revisarfechacontratoinsert();
       public          postgres    false    5            *           1255    16476    rol_delete(integer)    FUNCTION     �   CREATE FUNCTION public.rol_delete(id_rol_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "rol"
   where "id_rol"= $1; 
 END;$_$;
 3   DROP FUNCTION public.rol_delete(id_rol_0 integer);
       public          postgres    false    5            K           1255    16477    rol_insert(character varying)    FUNCTION     �   CREATE FUNCTION public.rol_insert(nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into rol(nombre)values("nombre_1"); 
  END;$$;
 =   DROP FUNCTION public.rol_insert(nombre_1 character varying);
       public          postgres    false    5            7           1255    16478 &   rol_update(integer, character varying)    FUNCTION     �   CREATE FUNCTION public.rol_update(id_rol_0 integer, nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "rol"
 SET nombre=$2 where "id_rol"= $1; 
 END;$_$;
 O   DROP FUNCTION public.rol_update(id_rol_0 integer, nombre_1 character varying);
       public          postgres    false    5            #           1255    16479    select_all_actividad()    FUNCTION       CREATE FUNCTION public.select_all_actividad() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listactividad refcursor;
BEGIN
       OPEN listactividad FOR 
		 Select * from actividad order by actividad.id_actividad ;
	    RETURN listactividad;
		END;
       $$;
 -   DROP FUNCTION public.select_all_actividad();
       public          postgres    false    5                       1255    16480    select_all_adopcion()    FUNCTION       CREATE FUNCTION public.select_all_adopcion() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listadopcion refcursor;
BEGIN
       OPEN listadopcion FOR 
		 Select * from adopcion order by adopcion.id_adopcion ;
	    RETURN listadopcion;
		END;
       $$;
 ,   DROP FUNCTION public.select_all_adopcion();
       public          postgres    false    5            �            1255    16481    select_all_animal()    FUNCTION     �  CREATE FUNCTION public.select_all_animal() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listanimal refcursor;
BEGIN
       OPEN listanimal FOR 
	SELECT 
        a.id_animal,
        a.nombre,
        a.id_raza,
        a.edad,
        a.peso,
        CURRENT_DATE - a.fecha_ingreso AS dias_refugio
	FROM 
        animal a order by a.id_animal ;
RETURN listanimal;
END;
       $$;
 *   DROP FUNCTION public.select_all_animal();
       public          postgres    false    5            ?           1255    16482    select_all_clinica()    FUNCTION       CREATE FUNCTION public.select_all_clinica() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listclinica refcursor;
BEGIN
       OPEN listclinica FOR 
		 Select * from clinica order by clinica.id_clinica ;
	    RETURN listclinica;
		END;
       $$;
 +   DROP FUNCTION public.select_all_clinica();
       public          postgres    false    5                        1255    16483    select_all_contrato()    FUNCTION       CREATE FUNCTION public.select_all_contrato() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listcontrato refcursor;
BEGIN
       OPEN listcontrato FOR 
		 Select * from contrato order by contrato.id_contrato ;
	    RETURN listcontrato;
		END;
       $$;
 ,   DROP FUNCTION public.select_all_contrato();
       public          postgres    false    5                       1255    16484    select_all_donacion()    FUNCTION       CREATE FUNCTION public.select_all_donacion() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listdonacion refcursor;
BEGIN
       OPEN listdonacion FOR 
		 Select * from donacion order by donacion.id_donacion ;
	    RETURN listdonacion;
		END;
       $$;
 ,   DROP FUNCTION public.select_all_donacion();
       public          postgres    false    5            �            1255    16485    select_all_especialidad()    FUNCTION     +  CREATE FUNCTION public.select_all_especialidad() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listespecialidad refcursor;
BEGIN
       OPEN listespecialidad FOR 
		 Select * from especialidad order by especialidad.id_especialidad ;
	    RETURN listespecialidad;
		END;
       $$;
 0   DROP FUNCTION public.select_all_especialidad();
       public          postgres    false    5            Q           1255    16486    select_all_especie()    FUNCTION       CREATE FUNCTION public.select_all_especie() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listespecie refcursor;
BEGIN
       OPEN listespecie FOR 
		 Select * from especie order by especie.id_especie ;
	    RETURN listespecie;
		END;
       $$;
 +   DROP FUNCTION public.select_all_especie();
       public          postgres    false    5            %           1255    16487    select_all_programacion()    FUNCTION     +  CREATE FUNCTION public.select_all_programacion() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listprogramacion refcursor;
BEGIN
       OPEN listprogramacion FOR 
		 Select * from programacion order by programacion.id_programacion ;
	    RETURN listprogramacion;
		END;
       $$;
 0   DROP FUNCTION public.select_all_programacion();
       public          postgres    false    5            A           1255    16488    select_all_proveedor()    FUNCTION       CREATE FUNCTION public.select_all_proveedor() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listproveedor refcursor;
BEGIN
       OPEN listproveedor FOR 
		 Select * from proveedor order by proveedor.id_proveedor ;
	    RETURN listproveedor;
		END;
       $$;
 -   DROP FUNCTION public.select_all_proveedor();
       public          postgres    false    5            X           1255    16489    select_all_provincia()    FUNCTION       CREATE FUNCTION public.select_all_provincia() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listprovincia refcursor;
BEGIN
       OPEN listprovincia FOR 
		 Select * from provincia order by provincia.id_provincia ;
	    RETURN listprovincia;
		END;
       $$;
 -   DROP FUNCTION public.select_all_provincia();
       public          postgres    false    5                       1255    16490    select_all_raza()    FUNCTION     �   CREATE FUNCTION public.select_all_raza() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listraza refcursor;
BEGIN
       OPEN listraza FOR 
		 Select * from raza order by raza.id_raza ;
	    RETURN listraza;
		END;
       $$;
 (   DROP FUNCTION public.select_all_raza();
       public          postgres    false    5            �            1255    16491    select_all_rol()    FUNCTION     �   CREATE FUNCTION public.select_all_rol() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listrol refcursor;
BEGIN
       OPEN listrol FOR 
		 Select * from rol order by rol.id_rol ;
	    RETURN listrol;
		END;
       $$;
 '   DROP FUNCTION public.select_all_rol();
       public          postgres    false    5            .           1255    16492    select_all_tipoproveedor()    FUNCTION     3  CREATE FUNCTION public.select_all_tipoproveedor() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listtipoproveedor refcursor;
BEGIN
       OPEN listtipoproveedor FOR 
		 Select * from tipoproveedor order by tipoproveedor.id_tipo_proveedor ;
	    RETURN listtipoproveedor;
		END;
       $$;
 1   DROP FUNCTION public.select_all_tipoproveedor();
       public          postgres    false    5            \           1255    16493    select_all_tiposervicio()    FUNCTION     ,  CREATE FUNCTION public.select_all_tiposervicio() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listtiposervicio refcursor;
BEGIN
       OPEN listtiposervicio FOR 
		 Select * from tiposervicio order by tiposervicio.id_tipo_servicio ;
	    RETURN listtiposervicio;
		END;
       $$;
 0   DROP FUNCTION public.select_all_tiposervicio();
       public          postgres    false    5            F           1255    16494    select_all_usuario()    FUNCTION       CREATE FUNCTION public.select_all_usuario() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listusuario refcursor;
BEGIN
       OPEN listusuario FOR 
		 Select * from usuario order by usuario.id_usuario ;
	    RETURN listusuario;
		END;
       $$;
 +   DROP FUNCTION public.select_all_usuario();
       public          postgres    false    5            g           1255    16495    select_all_usuariousuarios()    FUNCTION     -  CREATE FUNCTION public.select_all_usuariousuarios() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listusuario refcursor;
BEGIN
       OPEN listusuario FOR 
		 Select * from usuario where id_rol = 1 order by usuario.id_usuario;
		 
	    RETURN listusuario;
		END;
       $$;
 3   DROP FUNCTION public.select_all_usuariousuarios();
       public          postgres    false    5            2           1255    16496    select_all_veterinario()    FUNCTION     "  CREATE FUNCTION public.select_all_veterinario() RETURNS refcursor
    LANGUAGE plpgsql
    AS $$
DECLARE
       listveterinario refcursor;
BEGIN
       OPEN listveterinario FOR 
		 Select * from veterinario order by veterinario.id_proveedor ;
	    RETURN listveterinario;
		END;
       $$;
 /   DROP FUNCTION public.select_all_veterinario();
       public          postgres    false    5            5           1255    16497    tipoproveedor_delete(integer)    FUNCTION     �   CREATE FUNCTION public.tipoproveedor_delete(id_tipo_proveedor_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "tipoproveedor"
   where "id_tipo_proveedor"= $1; 
 END;$_$;
 H   DROP FUNCTION public.tipoproveedor_delete(id_tipo_proveedor_0 integer);
       public          postgres    false    5            6           1255    16498 '   tipoproveedor_insert(character varying)    FUNCTION     �   CREATE FUNCTION public.tipoproveedor_insert(nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into tipoproveedor(nombre)values("nombre_1"); 
  END;$$;
 G   DROP FUNCTION public.tipoproveedor_insert(nombre_1 character varying);
       public          postgres    false    5            I           1255    16499 0   tipoproveedor_update(integer, character varying)    FUNCTION     �   CREATE FUNCTION public.tipoproveedor_update(id_tipo_proveedor_0 integer, nombre_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "tipoproveedor"
 SET nombre=$2 where "id_tipo_proveedor"= $1; 
 END;$_$;
 d   DROP FUNCTION public.tipoproveedor_update(id_tipo_proveedor_0 integer, nombre_1 character varying);
       public          postgres    false    5                       1255    16500    tiposervicio_delete(integer)    FUNCTION     �   CREATE FUNCTION public.tiposervicio_delete(id_tipo_servicio_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "tiposervicio"
   where "id_tipo_servicio"= $1; 
 END;$_$;
 F   DROP FUNCTION public.tiposervicio_delete(id_tipo_servicio_0 integer);
       public          postgres    false    5            f           1255    16501 &   tiposervicio_insert(character varying)    FUNCTION     �   CREATE FUNCTION public.tiposervicio_insert(descripcion_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into tiposervicio(descripcion)values("descripcion_1"); 
  END;$$;
 K   DROP FUNCTION public.tiposervicio_insert(descripcion_1 character varying);
       public          postgres    false    5            j           1255    16502 /   tiposervicio_update(integer, character varying)    FUNCTION     �   CREATE FUNCTION public.tiposervicio_update(id_tipo_servicio_0 integer, descripcion_1 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "tiposervicio"
 SET descripcion=$2 where "id_tipo_servicio"= $1; 
 END;$_$;
 g   DROP FUNCTION public.tiposervicio_update(id_tipo_servicio_0 integer, descripcion_1 character varying);
       public          postgres    false    5            q           1255    16503    usuario_delete(integer)    FUNCTION     �   CREATE FUNCTION public.usuario_delete(id_usuario_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "usuario"
   where "id_usuario"= $1; 
 END;$_$;
 ;   DROP FUNCTION public.usuario_delete(id_usuario_0 integer);
       public          postgres    false    5            >           1255    16504 =   usuario_insert(integer, character varying, character varying)    FUNCTION     $  CREATE FUNCTION public.usuario_insert(id_rol_1 integer, nombre_usuario_2 character varying, clave_3 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into usuario(id_rol,nombre_usuario,clave)values("id_rol_1","nombre_usuario_2",md5(clave_3 || 'salt1')); 
  END;$$;
 v   DROP FUNCTION public.usuario_insert(id_rol_1 integer, nombre_usuario_2 character varying, clave_3 character varying);
       public          postgres    false    5                       1255    16505 F   usuario_update(integer, integer, character varying, character varying)    FUNCTION     /  CREATE FUNCTION public.usuario_update(id_usuario_0 integer, id_rol_1 integer, nombre_usuario_2 character varying, clave_3 character varying) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "usuario"
 SET id_rol=$2,nombre_usuario=$3,clave=md5($4 || 'salt1') where "id_usuario"= $1; 
 END;$_$;
 �   DROP FUNCTION public.usuario_update(id_usuario_0 integer, id_rol_1 integer, nombre_usuario_2 character varying, clave_3 character varying);
       public          postgres    false    5            ;           1255    16506    validarusuario()    FUNCTION     �  CREATE FUNCTION public.validarusuario() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	existe boolean;
BEGIN 
	SELECT EXISTS(
		SELECT 1
		FROM usuario
		WHERE nombre_usuario = NEW.nombre_usuario
	) INTO existe;

-- Si el usuario existe, lanzar una excepción
    IF existe THEN
        RAISE EXCEPTION 'El usuario ya existe';
    END IF;
    RETURN NEW;
END;
$$;
 '   DROP FUNCTION public.validarusuario();
       public          postgres    false    5            o           1255    16507    verificar_contrato_activo()    FUNCTION     I  CREATE FUNCTION public.verificar_contrato_activo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  -- Verificar si hay alguna superposición de contratos para el mismo proveedor
  IF EXISTS (
    SELECT 1
    FROM public.contrato
    WHERE id_proveedor = NEW.id_proveedor
      AND id_contrato <> NEW.id_contrato
      AND fecha_terminacion > NEW.fecha_inicio
      AND fecha_inicio < NEW.fecha_terminacion
  ) THEN
    RAISE EXCEPTION 'El proveedor % ya tiene un contrato activo en el período especificado', NEW.id_proveedor;
  END IF;

  RETURN NEW;
END;
$$;
 2   DROP FUNCTION public.verificar_contrato_activo();
       public          postgres    false    5            �            1255    16508    veterinario_delete(integer)    FUNCTION     �   CREATE FUNCTION public.veterinario_delete(id_proveedor_0 integer) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 delete  from "veterinario"
   where "id_proveedor"= $1; 
 END;$_$;
 A   DROP FUNCTION public.veterinario_delete(id_proveedor_0 integer);
       public          postgres    false    5            i           1255    16509 R   veterinario_insert(integer, integer, integer, character varying, double precision)    FUNCTION     �  CREATE FUNCTION public.veterinario_insert(id_proveedor_0 integer, id_clinica_1 integer, id_especialidad_2 integer, fax_3 character varying, distancia_ciudad_4 double precision) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN 
 insert into veterinario(id_proveedor,id_clinica,id_especialidad,fax,distancia_ciudad)values("id_proveedor_0","id_clinica_1","id_especialidad_2","fax_3","distancia_ciudad_4"); 
  END;$$;
 �   DROP FUNCTION public.veterinario_insert(id_proveedor_0 integer, id_clinica_1 integer, id_especialidad_2 integer, fax_3 character varying, distancia_ciudad_4 double precision);
       public          postgres    false    5            l           1255    16510 R   veterinario_update(integer, integer, integer, character varying, double precision)    FUNCTION     `  CREATE FUNCTION public.veterinario_update(id_proveedor_0 integer, id_clinica_1 integer, id_especialidad_2 integer, fax_3 character varying, distancia_ciudad_4 double precision) RETURNS void
    LANGUAGE plpgsql
    AS $_$BEGIN 
 update "veterinario"
 SET id_clinica=$2,id_especialidad=$3,fax=$4,distancia_ciudad=$5 where "id_proveedor"= $1; 
 END;$_$;
 �   DROP FUNCTION public.veterinario_update(id_proveedor_0 integer, id_clinica_1 integer, id_especialidad_2 integer, fax_3 character varying, distancia_ciudad_4 double precision);
       public          postgres    false    5            -           1255    16511    vigenciaprograma()    FUNCTION     @  CREATE FUNCTION public.vigenciaprograma() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
Declare
	fechaNueva date;
	horaNueva time without time zone;
BEGIN
	SELECT INTO fechaNueva fecha from actividad where id_actividad = NEW.id_actividad;
	SELECT INTO horaNueva hora from actividad where id_actividad = NEW.id_actividad;

	IF NOT (fechaNueva > current_date OR (fechaNueva = current_date AND horaNueva >= current_time(0)::time without time zone)) THEN
		RAISE Exception 'Se intentó asignar una actividad que no está vigente';
	END IF;
		
	RETURN NEW;
END;
$$;
 )   DROP FUNCTION public.vigenciaprograma();
       public          postgres    false    5            �            1259    16512 	   actividad    TABLE     �   CREATE TABLE public.actividad (
    id_actividad integer NOT NULL,
    fecha date,
    hora time without time zone,
    id_contrato integer,
    descripcion_actividad text
);
    DROP TABLE public.actividad;
       public         heap    postgres    false    5            �            1259    16517    actividad_id_actividad_seq    SEQUENCE     �   CREATE SEQUENCE public.actividad_id_actividad_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.actividad_id_actividad_seq;
       public          postgres    false    215    5                       0    0    actividad_id_actividad_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.actividad_id_actividad_seq OWNED BY public.actividad.id_actividad;
          public          postgres    false    216            �            1259    16522    animal    TABLE     �   CREATE TABLE public.animal (
    id_animal integer NOT NULL,
    nombre character varying(255),
    id_raza integer,
    edad integer,
    peso double precision,
    fecha_ingreso date
);
    DROP TABLE public.animal;
       public         heap    postgres    false    5            �            1259    16525    animal_id_animal_seq    SEQUENCE     }   CREATE SEQUENCE public.animal_id_animal_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.animal_id_animal_seq;
       public          postgres    false    217    5                       0    0    animal_id_animal_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.animal_id_animal_seq OWNED BY public.animal.id_animal;
          public          postgres    false    218            �            1259    24598 
   animal_seq    SEQUENCE     t   CREATE SEQUENCE public.animal_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.animal_seq;
       public          postgres    false    5            �            1259    16526    clinica    TABLE     m   CREATE TABLE public.clinica (
    id_clinica integer NOT NULL,
    nombre character varying(100) NOT NULL
);
    DROP TABLE public.clinica;
       public         heap    postgres    false    5            �            1259    16529    clinica_id_clinica_seq    SEQUENCE        CREATE SEQUENCE public.clinica_id_clinica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.clinica_id_clinica_seq;
       public          postgres    false    5    219                       0    0    clinica_id_clinica_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.clinica_id_clinica_seq OWNED BY public.clinica.id_clinica;
          public          postgres    false    220            �            1259    16530    contrato    TABLE     �   CREATE TABLE public.contrato (
    id_contrato integer NOT NULL,
    id_proveedor integer,
    fecha_inicio date,
    fecha_terminacion date,
    fecha_conciliacion date,
    descripcion text,
    precio_base numeric(10,2)
);
    DROP TABLE public.contrato;
       public         heap    postgres    false    5            �            1259    16535    contrato_id_contrato_seq    SEQUENCE     �   CREATE SEQUENCE public.contrato_id_contrato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.contrato_id_contrato_seq;
       public          postgres    false    221    5                       0    0    contrato_id_contrato_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.contrato_id_contrato_seq OWNED BY public.contrato.id_contrato;
          public          postgres    false    222            �            1259    16540    especialidad    TABLE     w   CREATE TABLE public.especialidad (
    id_especialidad integer NOT NULL,
    nombre character varying(100) NOT NULL
);
     DROP TABLE public.especialidad;
       public         heap    postgres    false    5            �            1259    16543     especialidad_id_especialidad_seq    SEQUENCE     �   CREATE SEQUENCE public.especialidad_id_especialidad_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.especialidad_id_especialidad_seq;
       public          postgres    false    5    223                       0    0     especialidad_id_especialidad_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.especialidad_id_especialidad_seq OWNED BY public.especialidad.id_especialidad;
          public          postgres    false    224            �            1259    16544    especie    TABLE     m   CREATE TABLE public.especie (
    id_especie integer NOT NULL,
    nombre character varying(255) NOT NULL
);
    DROP TABLE public.especie;
       public         heap    postgres    false    5            �            1259    16547    especie_id_especie_seq    SEQUENCE        CREATE SEQUENCE public.especie_id_especie_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.especie_id_especie_seq;
       public          postgres    false    225    5                       0    0    especie_id_especie_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.especie_id_especie_seq OWNED BY public.especie.id_especie;
          public          postgres    false    226            �            1259    16548    programacion    TABLE     |   CREATE TABLE public.programacion (
    id_programacion integer NOT NULL,
    id_actividad integer,
    id_animal integer
);
     DROP TABLE public.programacion;
       public         heap    postgres    false    5            �            1259    16551     programacion_id_programacion_seq    SEQUENCE     �   CREATE SEQUENCE public.programacion_id_programacion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.programacion_id_programacion_seq;
       public          postgres    false    227    5                       0    0     programacion_id_programacion_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.programacion_id_programacion_seq OWNED BY public.programacion.id_programacion;
          public          postgres    false    228            �            1259    16552 	   proveedor    TABLE     Z  CREATE TABLE public.proveedor (
    id_proveedor integer NOT NULL,
    nombre character varying(100),
    id_provincia integer,
    id_tipo_servicio integer,
    id_tipo_proveedor integer,
    direccion character varying(255),
    telefono character varying(20),
    email character varying(100),
    nombre_responsable character varying(100)
);
    DROP TABLE public.proveedor;
       public         heap    postgres    false    5            �            1259    16557    proveedor_id_proveedor_seq    SEQUENCE     �   CREATE SEQUENCE public.proveedor_id_proveedor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.proveedor_id_proveedor_seq;
       public          postgres    false    229    5                        0    0    proveedor_id_proveedor_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.proveedor_id_proveedor_seq OWNED BY public.proveedor.id_proveedor;
          public          postgres    false    230            �            1259    16558 	   provincia    TABLE     q   CREATE TABLE public.provincia (
    id_provincia integer NOT NULL,
    nombre character varying(100) NOT NULL
);
    DROP TABLE public.provincia;
       public         heap    postgres    false    5            �            1259    16561    provincia_id_provincia_seq    SEQUENCE     �   CREATE SEQUENCE public.provincia_id_provincia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.provincia_id_provincia_seq;
       public          postgres    false    231    5            !           0    0    provincia_id_provincia_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.provincia_id_provincia_seq OWNED BY public.provincia.id_provincia;
          public          postgres    false    232            �            1259    16562    raza    TABLE     �   CREATE TABLE public.raza (
    id_raza integer NOT NULL,
    nombre character varying(255) NOT NULL,
    id_especie integer NOT NULL
);
    DROP TABLE public.raza;
       public         heap    postgres    false    5            �            1259    16565    raza_id_raza_seq    SEQUENCE     y   CREATE SEQUENCE public.raza_id_raza_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.raza_id_raza_seq;
       public          postgres    false    233    5            "           0    0    raza_id_raza_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.raza_id_raza_seq OWNED BY public.raza.id_raza;
          public          postgres    false    234            �            1259    16566    rol    TABLE     e   CREATE TABLE public.rol (
    id_rol integer NOT NULL,
    nombre character varying(255) NOT NULL
);
    DROP TABLE public.rol;
       public         heap    postgres    false    5            �            1259    16569    rol_id_rol_seq    SEQUENCE     w   CREATE SEQUENCE public.rol_id_rol_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.rol_id_rol_seq;
       public          postgres    false    235    5            #           0    0    rol_id_rol_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.rol_id_rol_seq OWNED BY public.rol.id_rol;
          public          postgres    false    236            �            1259    16570    tipoproveedor    TABLE     q   CREATE TABLE public.tipoproveedor (
    id_tipo_proveedor integer NOT NULL,
    nombre character varying(100)
);
 !   DROP TABLE public.tipoproveedor;
       public         heap    postgres    false    5            �            1259    16573 #   tipoproveedor_id_tipo_proveedor_seq    SEQUENCE     �   CREATE SEQUENCE public.tipoproveedor_id_tipo_proveedor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 :   DROP SEQUENCE public.tipoproveedor_id_tipo_proveedor_seq;
       public          postgres    false    5    237            $           0    0 #   tipoproveedor_id_tipo_proveedor_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public.tipoproveedor_id_tipo_proveedor_seq OWNED BY public.tipoproveedor.id_tipo_proveedor;
          public          postgres    false    238            �            1259    16574    tiposervicio    TABLE     |   CREATE TABLE public.tiposervicio (
    id_tipo_servicio integer NOT NULL,
    descripcion character varying(50) NOT NULL
);
     DROP TABLE public.tiposervicio;
       public         heap    postgres    false    5            �            1259    16577 !   tiposervicio_id_tipo_servicio_seq    SEQUENCE     �   CREATE SEQUENCE public.tiposervicio_id_tipo_servicio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 8   DROP SEQUENCE public.tiposervicio_id_tipo_servicio_seq;
       public          postgres    false    5    239            %           0    0 !   tiposervicio_id_tipo_servicio_seq    SEQUENCE OWNED BY     g   ALTER SEQUENCE public.tiposervicio_id_tipo_servicio_seq OWNED BY public.tiposervicio.id_tipo_servicio;
          public          postgres    false    240            �            1259    24582    tokens    TABLE     �   CREATE TABLE public.tokens (
    id bigint NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255),
    user_id bigint
);
    DROP TABLE public.tokens;
       public         heap    postgres    false    5            �            1259    24599 
   tokens_seq    SEQUENCE     t   CREATE SEQUENCE public.tokens_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.tokens_seq;
       public          postgres    false    5            �            1259    16578    usuario    TABLE     �   CREATE TABLE public.usuario (
    id_usuario bigint NOT NULL,
    id_rol integer,
    nombre_usuario character varying(255),
    clave character varying(255)
);
    DROP TABLE public.usuario;
       public         heap    postgres    false    5            �            1259    16581    usuario_id_usuario_seq    SEQUENCE        CREATE SEQUENCE public.usuario_id_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.usuario_id_usuario_seq;
       public          postgres    false    5    241            &           0    0    usuario_id_usuario_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;
          public          postgres    false    242            �            1259    16582    veterinario    TABLE     �   CREATE TABLE public.veterinario (
    id_proveedor integer NOT NULL,
    id_clinica integer,
    id_especialidad integer,
    fax character varying(20),
    distancia_ciudad double precision
);
    DROP TABLE public.veterinario;
       public         heap    postgres    false    5                       2604    16585    actividad id_actividad    DEFAULT     �   ALTER TABLE ONLY public.actividad ALTER COLUMN id_actividad SET DEFAULT nextval('public.actividad_id_actividad_seq'::regclass);
 E   ALTER TABLE public.actividad ALTER COLUMN id_actividad DROP DEFAULT;
       public          postgres    false    216    215                       2604    16587    animal id_animal    DEFAULT     t   ALTER TABLE ONLY public.animal ALTER COLUMN id_animal SET DEFAULT nextval('public.animal_id_animal_seq'::regclass);
 ?   ALTER TABLE public.animal ALTER COLUMN id_animal DROP DEFAULT;
       public          postgres    false    218    217                       2604    16588    clinica id_clinica    DEFAULT     x   ALTER TABLE ONLY public.clinica ALTER COLUMN id_clinica SET DEFAULT nextval('public.clinica_id_clinica_seq'::regclass);
 A   ALTER TABLE public.clinica ALTER COLUMN id_clinica DROP DEFAULT;
       public          postgres    false    220    219                       2604    16589    contrato id_contrato    DEFAULT     |   ALTER TABLE ONLY public.contrato ALTER COLUMN id_contrato SET DEFAULT nextval('public.contrato_id_contrato_seq'::regclass);
 C   ALTER TABLE public.contrato ALTER COLUMN id_contrato DROP DEFAULT;
       public          postgres    false    222    221                       2604    16591    especialidad id_especialidad    DEFAULT     �   ALTER TABLE ONLY public.especialidad ALTER COLUMN id_especialidad SET DEFAULT nextval('public.especialidad_id_especialidad_seq'::regclass);
 K   ALTER TABLE public.especialidad ALTER COLUMN id_especialidad DROP DEFAULT;
       public          postgres    false    224    223                       2604    16592    especie id_especie    DEFAULT     x   ALTER TABLE ONLY public.especie ALTER COLUMN id_especie SET DEFAULT nextval('public.especie_id_especie_seq'::regclass);
 A   ALTER TABLE public.especie ALTER COLUMN id_especie DROP DEFAULT;
       public          postgres    false    226    225                       2604    16593    programacion id_programacion    DEFAULT     �   ALTER TABLE ONLY public.programacion ALTER COLUMN id_programacion SET DEFAULT nextval('public.programacion_id_programacion_seq'::regclass);
 K   ALTER TABLE public.programacion ALTER COLUMN id_programacion DROP DEFAULT;
       public          postgres    false    228    227                       2604    16594    proveedor id_proveedor    DEFAULT     �   ALTER TABLE ONLY public.proveedor ALTER COLUMN id_proveedor SET DEFAULT nextval('public.proveedor_id_proveedor_seq'::regclass);
 E   ALTER TABLE public.proveedor ALTER COLUMN id_proveedor DROP DEFAULT;
       public          postgres    false    230    229                       2604    16595    provincia id_provincia    DEFAULT     �   ALTER TABLE ONLY public.provincia ALTER COLUMN id_provincia SET DEFAULT nextval('public.provincia_id_provincia_seq'::regclass);
 E   ALTER TABLE public.provincia ALTER COLUMN id_provincia DROP DEFAULT;
       public          postgres    false    232    231                       2604    16596    raza id_raza    DEFAULT     l   ALTER TABLE ONLY public.raza ALTER COLUMN id_raza SET DEFAULT nextval('public.raza_id_raza_seq'::regclass);
 ;   ALTER TABLE public.raza ALTER COLUMN id_raza DROP DEFAULT;
       public          postgres    false    234    233                       2604    16597 
   rol id_rol    DEFAULT     h   ALTER TABLE ONLY public.rol ALTER COLUMN id_rol SET DEFAULT nextval('public.rol_id_rol_seq'::regclass);
 9   ALTER TABLE public.rol ALTER COLUMN id_rol DROP DEFAULT;
       public          postgres    false    236    235                       2604    16598    tipoproveedor id_tipo_proveedor    DEFAULT     �   ALTER TABLE ONLY public.tipoproveedor ALTER COLUMN id_tipo_proveedor SET DEFAULT nextval('public.tipoproveedor_id_tipo_proveedor_seq'::regclass);
 N   ALTER TABLE public.tipoproveedor ALTER COLUMN id_tipo_proveedor DROP DEFAULT;
       public          postgres    false    238    237                       2604    16599    tiposervicio id_tipo_servicio    DEFAULT     �   ALTER TABLE ONLY public.tiposervicio ALTER COLUMN id_tipo_servicio SET DEFAULT nextval('public.tiposervicio_id_tipo_servicio_seq'::regclass);
 L   ALTER TABLE public.tiposervicio ALTER COLUMN id_tipo_servicio DROP DEFAULT;
       public          postgres    false    240    239                       2604    24587    usuario id_usuario    DEFAULT     x   ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);
 A   ALTER TABLE public.usuario ALTER COLUMN id_usuario DROP DEFAULT;
       public          postgres    false    242    241            �          0    16512 	   actividad 
   TABLE DATA           b   COPY public.actividad (id_actividad, fecha, hora, id_contrato, descripcion_actividad) FROM stdin;
    public          postgres    false    215   ��      �          0    16522    animal 
   TABLE DATA           W   COPY public.animal (id_animal, nombre, id_raza, edad, peso, fecha_ingreso) FROM stdin;
    public          postgres    false    217   {�      �          0    16526    clinica 
   TABLE DATA           5   COPY public.clinica (id_clinica, nombre) FROM stdin;
    public          postgres    false    219   ��      �          0    16530    contrato 
   TABLE DATA           �   COPY public.contrato (id_contrato, id_proveedor, fecha_inicio, fecha_terminacion, fecha_conciliacion, descripcion, precio_base) FROM stdin;
    public          postgres    false    221   /�      �          0    16540    especialidad 
   TABLE DATA           ?   COPY public.especialidad (id_especialidad, nombre) FROM stdin;
    public          postgres    false    223   ��      �          0    16544    especie 
   TABLE DATA           5   COPY public.especie (id_especie, nombre) FROM stdin;
    public          postgres    false    225   R�      �          0    16548    programacion 
   TABLE DATA           P   COPY public.programacion (id_programacion, id_actividad, id_animal) FROM stdin;
    public          postgres    false    227   ��                 0    16552 	   proveedor 
   TABLE DATA           �   COPY public.proveedor (id_proveedor, nombre, id_provincia, id_tipo_servicio, id_tipo_proveedor, direccion, telefono, email, nombre_responsable) FROM stdin;
    public          postgres    false    229   +�                0    16558 	   provincia 
   TABLE DATA           9   COPY public.provincia (id_provincia, nombre) FROM stdin;
    public          postgres    false    231   ��                0    16562    raza 
   TABLE DATA           ;   COPY public.raza (id_raza, nombre, id_especie) FROM stdin;
    public          postgres    false    233   ��                0    16566    rol 
   TABLE DATA           -   COPY public.rol (id_rol, nombre) FROM stdin;
    public          postgres    false    235   S�                0    16570    tipoproveedor 
   TABLE DATA           B   COPY public.tipoproveedor (id_tipo_proveedor, nombre) FROM stdin;
    public          postgres    false    237   ��      
          0    16574    tiposervicio 
   TABLE DATA           E   COPY public.tiposervicio (id_tipo_servicio, descripcion) FROM stdin;
    public          postgres    false    239   ʣ                0    24582    tokens 
   TABLE DATA           F   COPY public.tokens (id, expired, revoked, token, user_id) FROM stdin;
    public          postgres    false    244   i�                0    16578    usuario 
   TABLE DATA           L   COPY public.usuario (id_usuario, id_rol, nombre_usuario, clave) FROM stdin;
    public          postgres    false    241   h�                0    16582    veterinario 
   TABLE DATA           g   COPY public.veterinario (id_proveedor, id_clinica, id_especialidad, fax, distancia_ciudad) FROM stdin;
    public          postgres    false    243   �      '           0    0    actividad_id_actividad_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.actividad_id_actividad_seq', 54, true);
          public          postgres    false    216            (           0    0    animal_id_animal_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.animal_id_animal_seq', 20, true);
          public          postgres    false    218            )           0    0 
   animal_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.animal_seq', 1, false);
          public          postgres    false    245            *           0    0    clinica_id_clinica_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.clinica_id_clinica_seq', 8, true);
          public          postgres    false    220            +           0    0    contrato_id_contrato_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.contrato_id_contrato_seq', 15, true);
          public          postgres    false    222            ,           0    0     especialidad_id_especialidad_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.especialidad_id_especialidad_seq', 5, true);
          public          postgres    false    224            -           0    0    especie_id_especie_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.especie_id_especie_seq', 5, true);
          public          postgres    false    226            .           0    0     programacion_id_programacion_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.programacion_id_programacion_seq', 59, true);
          public          postgres    false    228            /           0    0    proveedor_id_proveedor_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.proveedor_id_proveedor_seq', 14, true);
          public          postgres    false    230            0           0    0    provincia_id_provincia_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.provincia_id_provincia_seq', 16, true);
          public          postgres    false    232            1           0    0    raza_id_raza_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.raza_id_raza_seq', 14, true);
          public          postgres    false    234            2           0    0    rol_id_rol_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.rol_id_rol_seq', 2, true);
          public          postgres    false    236            3           0    0 #   tipoproveedor_id_tipo_proveedor_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public.tipoproveedor_id_tipo_proveedor_seq', 3, true);
          public          postgres    false    238            4           0    0 !   tiposervicio_id_tipo_servicio_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.tiposervicio_id_tipo_servicio_seq', 9, true);
          public          postgres    false    240            5           0    0 
   tokens_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.tokens_seq', 51, true);
          public          postgres    false    246            6           0    0    usuario_id_usuario_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 7, true);
          public          postgres    false    242                       2606    16602    actividad actividad_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.actividad
    ADD CONSTRAINT actividad_pkey PRIMARY KEY (id_actividad);
 B   ALTER TABLE ONLY public.actividad DROP CONSTRAINT actividad_pkey;
       public            postgres    false    215                       2606    16608    animal animal_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.animal
    ADD CONSTRAINT animal_pkey PRIMARY KEY (id_animal);
 <   ALTER TABLE ONLY public.animal DROP CONSTRAINT animal_pkey;
       public            postgres    false    217                        2606    16610    clinica clinica_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.clinica
    ADD CONSTRAINT clinica_pkey PRIMARY KEY (id_clinica);
 >   ALTER TABLE ONLY public.clinica DROP CONSTRAINT clinica_pkey;
       public            postgres    false    219            $           2606    16612    contrato contrato_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT contrato_pkey PRIMARY KEY (id_contrato);
 @   ALTER TABLE ONLY public.contrato DROP CONSTRAINT contrato_pkey;
       public            postgres    false    221            &           2606    16616    especialidad especialidad_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.especialidad
    ADD CONSTRAINT especialidad_pkey PRIMARY KEY (id_especialidad);
 H   ALTER TABLE ONLY public.especialidad DROP CONSTRAINT especialidad_pkey;
       public            postgres    false    223            *           2606    16618    especie especie_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.especie
    ADD CONSTRAINT especie_pkey PRIMARY KEY (id_especie);
 >   ALTER TABLE ONLY public.especie DROP CONSTRAINT especie_pkey;
       public            postgres    false    225            .           2606    16620    programacion programacion_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.programacion
    ADD CONSTRAINT programacion_pkey PRIMARY KEY (id_programacion);
 H   ALTER TABLE ONLY public.programacion DROP CONSTRAINT programacion_pkey;
       public            postgres    false    227            0           2606    16622    proveedor proveedor_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (id_proveedor);
 B   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT proveedor_pkey;
       public            postgres    false    229            4           2606    16624    provincia provincia_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.provincia
    ADD CONSTRAINT provincia_pkey PRIMARY KEY (id_provincia);
 B   ALTER TABLE ONLY public.provincia DROP CONSTRAINT provincia_pkey;
       public            postgres    false    231            8           2606    16626    raza raza_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.raza
    ADD CONSTRAINT raza_pkey PRIMARY KEY (id_raza);
 8   ALTER TABLE ONLY public.raza DROP CONSTRAINT raza_pkey;
       public            postgres    false    233            <           2606    16628    rol rol_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (id_rol);
 6   ALTER TABLE ONLY public.rol DROP CONSTRAINT rol_pkey;
       public            postgres    false    235            @           2606    16630     tipoproveedor tipoproveedor_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY public.tipoproveedor
    ADD CONSTRAINT tipoproveedor_pkey PRIMARY KEY (id_tipo_proveedor);
 J   ALTER TABLE ONLY public.tipoproveedor DROP CONSTRAINT tipoproveedor_pkey;
       public            postgres    false    237            D           2606    16632    tiposervicio tiposervicio_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.tiposervicio
    ADD CONSTRAINT tiposervicio_pkey PRIMARY KEY (id_tipo_servicio);
 H   ALTER TABLE ONLY public.tiposervicio DROP CONSTRAINT tiposervicio_pkey;
       public            postgres    false    239            L           2606    24586    tokens tokens_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.tokens DROP CONSTRAINT tokens_pkey;
       public            postgres    false    244            N           2606    24597 "   tokens ukna3v9f8s7ucnj16tylrs822qj 
   CONSTRAINT     ^   ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT ukna3v9f8s7ucnj16tylrs822qj UNIQUE (token);
 L   ALTER TABLE ONLY public.tokens DROP CONSTRAINT ukna3v9f8s7ucnj16tylrs822qj;
       public            postgres    false    244            F           2606    16634    tiposervicio uniq 
   CONSTRAINT     S   ALTER TABLE ONLY public.tiposervicio
    ADD CONSTRAINT uniq UNIQUE (descripcion);
 ;   ALTER TABLE ONLY public.tiposervicio DROP CONSTRAINT uniq;
       public            postgres    false    239            "           2606    16636    clinica uniqclin 
   CONSTRAINT     M   ALTER TABLE ONLY public.clinica
    ADD CONSTRAINT uniqclin UNIQUE (nombre);
 :   ALTER TABLE ONLY public.clinica DROP CONSTRAINT uniqclin;
       public            postgres    false    219            ,           2606    24577    especie uniqesp 
   CONSTRAINT     L   ALTER TABLE ONLY public.especie
    ADD CONSTRAINT uniqesp UNIQUE (nombre);
 9   ALTER TABLE ONLY public.especie DROP CONSTRAINT uniqesp;
       public            postgres    false    225            (           2606    16640    especialidad uniqespecialidad 
   CONSTRAINT     Z   ALTER TABLE ONLY public.especialidad
    ADD CONSTRAINT uniqespecialidad UNIQUE (nombre);
 G   ALTER TABLE ONLY public.especialidad DROP CONSTRAINT uniqespecialidad;
       public            postgres    false    223            6           2606    16642    provincia uniqprov 
   CONSTRAINT     O   ALTER TABLE ONLY public.provincia
    ADD CONSTRAINT uniqprov UNIQUE (nombre);
 <   ALTER TABLE ONLY public.provincia DROP CONSTRAINT uniqprov;
       public            postgres    false    231            2           2606    16644    proveedor uniqprovee 
   CONSTRAINT     Z   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT uniqprovee UNIQUE (telefono, email);
 >   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT uniqprovee;
       public            postgres    false    229    229            B           2606    16646    tipoproveedor uniqproveedor 
   CONSTRAINT     X   ALTER TABLE ONLY public.tipoproveedor
    ADD CONSTRAINT uniqproveedor UNIQUE (nombre);
 E   ALTER TABLE ONLY public.tipoproveedor DROP CONSTRAINT uniqproveedor;
       public            postgres    false    237            :           2606    24579    raza uniqraza 
   CONSTRAINT     J   ALTER TABLE ONLY public.raza
    ADD CONSTRAINT uniqraza UNIQUE (nombre);
 7   ALTER TABLE ONLY public.raza DROP CONSTRAINT uniqraza;
       public            postgres    false    233            >           2606    24581    rol uniqrol 
   CONSTRAINT     H   ALTER TABLE ONLY public.rol
    ADD CONSTRAINT uniqrol UNIQUE (nombre);
 5   ALTER TABLE ONLY public.rol DROP CONSTRAINT uniqrol;
       public            postgres    false    235            H           2606    24589    usuario usuario_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    241            J           2606    16654    veterinario veterinario_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.veterinario
    ADD CONSTRAINT veterinario_pkey PRIMARY KEY (id_proveedor);
 F   ALTER TABLE ONLY public.veterinario DROP CONSTRAINT veterinario_pkey;
       public            postgres    false    243            b           2620    16656 #   usuario chequea_usuarios_insertados    TRIGGER     �   CREATE TRIGGER chequea_usuarios_insertados BEFORE INSERT OR UPDATE ON public.usuario FOR EACH ROW EXECUTE FUNCTION public.validarusuario();
 <   DROP TRIGGER chequea_usuarios_insertados ON public.usuario;
       public          postgres    false    241    315            ]           2620    16657    actividad fechaactividadtr    TRIGGER     �   CREATE TRIGGER fechaactividadtr BEFORE INSERT OR UPDATE ON public.actividad FOR EACH ROW EXECUTE FUNCTION public.fechaactividad();
 3   DROP TRIGGER fechaactividadtr ON public.actividad;
       public          postgres    false    215    353            ^           2620    16660 #   contrato revisarfechacontratoedittr    TRIGGER     �   CREATE TRIGGER revisarfechacontratoedittr BEFORE UPDATE ON public.contrato FOR EACH ROW EXECUTE FUNCTION public.revisarfechacontrato();
 <   DROP TRIGGER revisarfechacontratoedittr ON public.contrato;
       public          postgres    false    357    221            _           2620    16661 %   contrato revisarfechacontratoinserttr    TRIGGER     �   CREATE TRIGGER revisarfechacontratoinserttr BEFORE INSERT ON public.contrato FOR EACH ROW EXECUTE FUNCTION public.revisarfechacontratoinsert();
 >   DROP TRIGGER revisarfechacontratoinserttr ON public.contrato;
       public          postgres    false    221    287            `           2620    16662 (   contrato trg_verificar_contrato_activotr    TRIGGER     �   CREATE TRIGGER trg_verificar_contrato_activotr BEFORE INSERT OR UPDATE ON public.contrato FOR EACH ROW EXECUTE FUNCTION public.verificar_contrato_activo();
 A   DROP TRIGGER trg_verificar_contrato_activotr ON public.contrato;
       public          postgres    false    367    221            a           2620    16663    programacion vigenciaprogramatr    TRIGGER     �   CREATE TRIGGER vigenciaprogramatr BEFORE INSERT OR UPDATE ON public.programacion FOR EACH ROW EXECUTE FUNCTION public.vigenciaprograma();
 8   DROP TRIGGER vigenciaprogramatr ON public.programacion;
       public          postgres    false    301    227            O           2606    16664 $   actividad actividad_id_contrato_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.actividad
    ADD CONSTRAINT actividad_id_contrato_fkey FOREIGN KEY (id_contrato) REFERENCES public.contrato(id_contrato) ON DELETE CASCADE;
 N   ALTER TABLE ONLY public.actividad DROP CONSTRAINT actividad_id_contrato_fkey;
       public          postgres    false    215    221    4900            P           2606    16674    animal animal_id_raza_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.animal
    ADD CONSTRAINT animal_id_raza_fkey FOREIGN KEY (id_raza) REFERENCES public.raza(id_raza);
 D   ALTER TABLE ONLY public.animal DROP CONSTRAINT animal_id_raza_fkey;
       public          postgres    false    233    217    4920            Q           2606    16679 #   contrato contrato_id_proveedor_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.contrato
    ADD CONSTRAINT contrato_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedor(id_proveedor) ON DELETE CASCADE;
 M   ALTER TABLE ONLY public.contrato DROP CONSTRAINT contrato_id_proveedor_fkey;
       public          postgres    false    221    4912    229            \           2606    24600 "   tokens fker5emrr5goy8cuv6jv2d91tg8    FK CONSTRAINT     �   ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT fker5emrr5goy8cuv6jv2d91tg8 FOREIGN KEY (user_id) REFERENCES public.usuario(id_usuario);
 L   ALTER TABLE ONLY public.tokens DROP CONSTRAINT fker5emrr5goy8cuv6jv2d91tg8;
       public          postgres    false    244    241    4936            R           2606    16689 +   programacion programacion_id_actividad_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.programacion
    ADD CONSTRAINT programacion_id_actividad_fkey FOREIGN KEY (id_actividad) REFERENCES public.actividad(id_actividad) ON DELETE CASCADE;
 U   ALTER TABLE ONLY public.programacion DROP CONSTRAINT programacion_id_actividad_fkey;
       public          postgres    false    215    227    4892            S           2606    16694 (   programacion programacion_id_animal_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.programacion
    ADD CONSTRAINT programacion_id_animal_fkey FOREIGN KEY (id_animal) REFERENCES public.animal(id_animal) ON DELETE CASCADE;
 R   ALTER TABLE ONLY public.programacion DROP CONSTRAINT programacion_id_animal_fkey;
       public          postgres    false    227    217    4894            T           2606    16699 %   proveedor proveedor_id_provincia_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_id_provincia_fkey FOREIGN KEY (id_provincia) REFERENCES public.provincia(id_provincia);
 O   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT proveedor_id_provincia_fkey;
       public          postgres    false    231    229    4916            U           2606    16704 *   proveedor proveedor_id_tipo_proveedor_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_id_tipo_proveedor_fkey FOREIGN KEY (id_tipo_proveedor) REFERENCES public.tipoproveedor(id_tipo_proveedor);
 T   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT proveedor_id_tipo_proveedor_fkey;
       public          postgres    false    229    4928    237            V           2606    16709 )   proveedor proveedor_id_tipo_servicio_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_id_tipo_servicio_fkey FOREIGN KEY (id_tipo_servicio) REFERENCES public.tiposervicio(id_tipo_servicio);
 S   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT proveedor_id_tipo_servicio_fkey;
       public          postgres    false    4932    239    229            W           2606    16714    raza raza_id_especie_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.raza
    ADD CONSTRAINT raza_id_especie_fkey FOREIGN KEY (id_especie) REFERENCES public.especie(id_especie);
 C   ALTER TABLE ONLY public.raza DROP CONSTRAINT raza_id_especie_fkey;
       public          postgres    false    233    4906    225            X           2606    16719    usuario usuario_id_rol_fkey    FK CONSTRAINT     {   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_id_rol_fkey FOREIGN KEY (id_rol) REFERENCES public.rol(id_rol);
 E   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_id_rol_fkey;
       public          postgres    false    241    4924    235            Y           2606    16724 '   veterinario veterinario_id_clinica_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.veterinario
    ADD CONSTRAINT veterinario_id_clinica_fkey FOREIGN KEY (id_clinica) REFERENCES public.clinica(id_clinica);
 Q   ALTER TABLE ONLY public.veterinario DROP CONSTRAINT veterinario_id_clinica_fkey;
       public          postgres    false    219    243    4896            Z           2606    16729 ,   veterinario veterinario_id_especialidad_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.veterinario
    ADD CONSTRAINT veterinario_id_especialidad_fkey FOREIGN KEY (id_especialidad) REFERENCES public.especialidad(id_especialidad);
 V   ALTER TABLE ONLY public.veterinario DROP CONSTRAINT veterinario_id_especialidad_fkey;
       public          postgres    false    223    4902    243            [           2606    16734 )   veterinario veterinario_id_proveedor_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.veterinario
    ADD CONSTRAINT veterinario_id_proveedor_fkey FOREIGN KEY (id_proveedor) REFERENCES public.proveedor(id_proveedor) ON DELETE CASCADE;
 S   ALTER TABLE ONLY public.veterinario DROP CONSTRAINT veterinario_id_proveedor_fkey;
       public          postgres    false    229    243    4912            �   �  x�}W���F�ɯ�8�Sw��	���TiF�H;�Cϐ����������;|̌DP!��s_J�4N�wI�.����>��	��o{��#=t\]���RG����xi����ϊG�����R�0���3t<J��4~Ѣ���'ţr� �1�"�S����M�0s��x�J��[ݫ���OP�^�@�f�3)���%�na����hg�t����Ւ�����i�;8]����ߌ����+��Vw���i���*m��{�٪��W� ��7�V#
H5�*jd�K���������7���jR��ɂ�ӓS>��n�^RN�j��o�\b����MB��Y�R6]�_؝��zQ2��E�[���O	�%�E���JR��Z\e��
F��:Y��k
���|�qЅ�x4LmE�NyP�h�%�N�^���?B���R���m1������;7�:���´p�Z(~�@$��V�	%Vċbm�"Im$�~����'��P����5�~�=�ŏ]��)����I�����-�ʒ��"��R=ײ�ֵ�W��:���>��Ώ0�7������ .'�[�002zV/�~�=WW��d��}�7�9è�E�/�2s�05�����Y���>�8��� 隆�C�˶ΰ���x��@��G��i��w[���̮Mc�7�$�c�Y���:�`h'�~U�f/K�U�P�;$�ӥ�I��V�c��5�FV�Ԕ�P�~�@mT<���#��Đ�-f����,:+�!���̞@&����&�G��&�gQ��gzNvQ5�l���_M5wp|o>R�Cc�Q�tJ����av�t>I�?~�T{C7���8�����L�z�i=�� o�J1�g9_:53��?/cĳ?O6������~��_ct�\৸R�y�-Gz�z9�@L�0i�u�H��N��'1�m1R˙]Y�
zS_�K���+p��og�^�|[Pj�R�<��{�X#��:�4AO���Jd�o��4���6k�Tn55k�ܴE��� �����j�ѩ)&g��r��~[���7��3����QIO�<f�a~� �	(��7Ӣ�kƷ��X3��m�Ia~�V#�!ͪ��]N@/���������7�F�w��r��� �iWLgj�-G<�;�0E۠�A[m6���I(�"�N���	4�������c_	�/�˶3.����b?M�y�Zd�HsҚ�Aa�~	���eM      �   2  x�U��N�0Dϳ_���ڱ��
H�.\�**!FmSѿgP�����0������h�	p�U3f6���4�Q������f��i�*Q.���|8�]F ;[����Ib5|mbA�"^��=��¶��Z\wC�)9��J��K����,���q�vi�����x��,�v{"_���Če�@��IԘ(�ҷ�V�s(�Wx�?��W��y�����$
:����C�vz$�2m�"Yc�Y&�Sk���<����XPC�⡗B��F[XK���7'�A��RȖ���C�Zd͋7��VGx3D�śrZ      �   b   x�3�H�+-�L,�222K�BR+��9]+
�R���L8�K�*
R���L9�s2���8}�
K���9�K�K�, ���=... �T      �   �  x���Mn�0���S��_�� �>@�݌i�"U��w��|���EK�P{7��{�H��PP����m�SY�-���oh�Q��<Hː)t�C'�G-�O�o����͠���^��3�(]:#�FA�O�z��:��ؠ'���ؑ��@sU*a_�M{+�uk�BM���P��R�P�W0�?���#M�ۑ��t�G&p�fd��]�5grc1��W���Ӄ�$��8A�6PTd�N����0���9�Fd:��dOq!f<7b}-�[���0�ca��c��&�8�IY�0���idLy�������i��>⹂j�3�w_�����Hf��:k�׈�~A�),���y�k���f�V���Js!�a�*�}�59kڑ��ݽӮ�C�s��crޢv�}���NE���6�.G�=�~7�\V�7�ӑ0��y"1��v��?m6�z�PZ      �   D   x�3�tO�K-J��2�t�,*�J���2�
���$r�p���&�ޜ����e����	���qqq ���      �   8   x�3�H-*��2�tO,��2�t��K���2��L/M�K�2��H�-.I-����� I�!      �   �   x���0��XL`��{I�u��k���ZÛ	�5W�Jy�1�/��JX�"�F)?ȦP�D%4w����u�T�KTȱ)ǖ\�Z��F]9v���}�X���cSn�%Ƕ�C�{�إ��~t���-�%�          w  x�}�Kn�0�׿NA��")��]�48E�t�c�t)�@r�����A�"0$y���sf(N�����e�N?NW6�sqȎ�썩��PԊ��\z�d[?����l�6t���Ğ�·`+A�v��ҥ������5�r��d#d�?[h$_�kL|bw]p��=5TOt���x	�֚ںQ��sC菺��v3��n���T5t��=�a����mC-��Ziz�dŌz�c������"�]���`���,�'�1h��e]�� ��ܓ�s�[��y�m%i�S�\�{R3�<��y�[� E	�h�u6��.��ط.o|d���
�S��>����9�ON+��\�M!s-��W�^�x1� v:X�O�_;����P�	~�)g{\�.��ext��1F��un���}�]�.�����݋�llL=�z�Χ@�.r�]�/RD�����b��h�9-G���GEI���S�B�.��[j.dq(@Y��)[����Ӆ>E��wh��G��'>/u䥏u-���{e�y���˃e70���
f,\ʝ�=۟�}u��O�,+���sd,E�ʩ���U\�/Ϋ�o�G������� 9Em&�Jʢ�,�FIq�W�6{��`��{wH�V�Ϫ����e         �   x�%�MJA��O v�ԥ�"1(�WnjLgh�y�'w�����|�MQTe�>7���#�=<�g/Hf�yiS�=��}V�K���+꼓CuC�|Co>�. �oi�(��mJ���w���j������#����l�>�浊��61�M�vN�u��P��?���fKZWۏ`�lW����֪�zrR�߯��;�MH         �   x�]�M
�0����9������U�PAZp�fJ�H�� �ȵG�Ō�.\�;ϧ�ƎQ{%(&k��c�P��bf��G�D�p&k'4�d齋�v�����4t�w%vtG�I�4?]4����С�k�L(�MV�)�ѿ��8��X����\��G���f
���њq�.�/��B�         $   x�3�tL����,.)JL�/�2���OI��c���� �
          3   x�3�K-I-��K,���2�t��-�I�M�+s:�d�y)�E\1z\\\ �,/      
   �   x��;1Dk�9(�j�����6�b)qVqB����h�b�n4zof}�<��B��Z�̵�36�B'�N˻;�eR�S.�{��E��ɳ9��R�	�Q���z-������GZ�������7�'<õ΁�3���{�5�         �   x��λn�0 ��Fn!�bh�Mu��f Jh ~�z�Ե:��O�����!e� ��y�����\jn����Q@��ʃ*zC<U���)�<�9:, zE�P�l����ǖo�L��(.Q[]{�/FWR�&%jw�e��P�>�<�Y�i�Oɒu��'�	���˻5�O�r:�q��Fpb���#ٿ^ZZun7^6Ngx�V��q��k��sC�:�E��6.s�         �   x����
�@ ���w��;o]�Q).�8�S#�����E��G��ҁ�mĬ�� ��`�r��+�E�r�%_�k3�I���2�)Ց��KN���V�yt
h���[d����Ϙ�z1���}Z��sYo�]��\,Y��zKf�5�b��5�         R   x�5�� !�޻�0����_ǉ3$�<���|F�}�C����1�n1:�h`�������(y7��C�ߧ����	��     