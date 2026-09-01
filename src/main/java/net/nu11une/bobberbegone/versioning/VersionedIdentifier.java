package net.nu11une.bobberbegone.versioning;

import net.nu11une.bobberbegone.BuildProperties;
import org.jetbrains.annotations.NotNull;
//? if >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.resources.ResourceLocation;
//?}

public class VersionedIdentifier {
    @NotNull
    //? if >=1.21.11 {
    /*public static Identifier of(@NotNull String namespace, @NotNull String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    *///?} else {
    public static ResourceLocation of(@NotNull String namespace, @NotNull String path) {
        //? if >1.20.5 {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
        //?} else {
        /*return new ResourceLocation(namespace, path);
        *///?}
    //?}
    }

    @NotNull
    //? if >=1.21.11 {
    /*public static Identifier of(@NotNull String path) {
    *///?} else {
    public static ResourceLocation of(@NotNull String path) {
    //?}
        return of(BuildProperties.MOD_ID, path);
    }

    @NotNull
    //? if >=1.21.11 {
    /*public static Identifier ofMinecraft(@NotNull String path) {
     *///?} else {
    public static ResourceLocation ofMinecraft(@NotNull String path) {
    //?}
        return of("minecraft", path);
    }
}
