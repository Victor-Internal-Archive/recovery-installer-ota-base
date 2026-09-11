DESCRIPTION = "Silly wire daemon for training wakeword and setting performance profile"
LICENSE = "Anki-Inc.-Proprietary"                                                                   
LIC_FILES_CHKSUM = "file://${COREBASE}/../victor/meta-qcom/files/anki-licenses/\                           
Anki-Inc.-Proprietary;md5=4b03b8ffef1b70b13d869dbce43e8f09"

S = "${UNPACKDIR}"
#UNPACKDIR = "${S}"

inherit externalsrc

EXTERNALSRC = "${WORKSPACE}/anki/recovery-installer/ota-code/"

do_clean:append () {
    dir = bb.data.expand("${EXTERNALSRC}", d)
    os.system('cd "%s" && rm main && rm vector-gobot/build/*' % dir)
}


run_victor() {
  export -n CCACHE_DISABLE
  export CCACHE_DIR="${HOME}/.ccache"
  env \
    -u AR \
    -u AS \
    -u BUILD_AR \
    -u BUILD_AS \
    -u BUILD_CC \
    -u BUILD_CCLD \
    -u BUILD_CFLAGS \
    -u BUILD_CPP \
    -u BUILD_CPPFLAGS \
    -u BUILD_CXX \
    -u BUILD_CXXFLAGS \
    -u BUILD_FC \
    -u CPPFLAGS \
    -u LC_ALL \
    -u LD \
    -u LDFLAGS \
    -u MAKE \
    -u NM \
    -u OBJCOPY \
    -u OBJDUMP \
    -u PATCH_GET \
    -u PKG_CONFIG_DIR \
    -u PKG_CONFIG_DISABLE_UNINSTALLED \
    -u PKG_CONFIG_LIBDIR \
    -u PKG_CONFIG_PATH \
    -u PKG_CONFIG_SYSROOT_DIR \
    -u PSEUDO_DISABLED \
    -u PSEUDO_UNLOAD \
    -u RANLIB \
    -u STRINGS \
    -u STRIP \
    -u TARGET_CFLAGS \
    -u TARGET_CPPFLAGS \
    -u TARGET_CXXFLAGS \
    -u TARGET_LDFLAGS \
    -u TOPLEVEL \
    -u WORKSPACE \
    -u base_bindir \
    -u base_libdir \
    -u base_prefix \
    -u base_sbindir \
    -u bindir \
    -u datadir \
    -u docdir \
    -u exec_prefix \
    -u includedir \
    -u infodir \
    -u libdir \
    -u libexecdir \
    -u localstatedir \
    -u mandir \
    -u nonarch_base_libdir \
    -u nonarch_libdir \
    -u oldincludedir \
    -u prefix \
    -u sbindir \
    -u servicedir \
    -u sharedstatedir \
    -u sysconfdir \
    -u systemd_system_unitdir \
    -u systemd_unitdir \
    -u systemd_user_unitdir \
    -u userfsdatadir \
    -i PATH=/usr/bin:/bin:/usr/sbin:/sbin HOME=$HOME PWD="${WORKSPACE}/anki/recovery-installer/ota-code" \
    "$@"
}

do_compile[pseudo] = "0"
do_compile[network] = "1"

do_compile() {
    cd "${EXTERNALSRC}"
    run_victor ./build.sh
}

do_install () {
    install -d ${D}/anki
    install -d ${D}/usr/bin
    install -d ${D}/usr/lib
    install -p -m 0755 ${WORKSPACE}/anki/recovery-installer/ankidev-signed.img.gz ${D}/anki/
    install -p -m 0755 ${WORKSPACE}/anki/recovery-installer/recovery*.gz ${D}/anki/
    install -p -m 0755 ${WORKSPACE}/anki/recovery-installer/ota-code/main ${D}/usr/bin/install-recovery
    install -p -m 0755 ${WORKSPACE}/anki/recovery-installer/libs/* ${D}/usr/lib
    install -p -m 0755 ${WORKSPACE}/anki/recovery-installer/ota-code/vector-gobot/build/* ${D}/usr/lib
}

FILES:${PN} += "anki/"
FILES:${PN} += "usr/bin/install-recovery"
FILES:${PN} += "usr/lib/libvector-gobot.so"

FILES:${PN}-dev = ""
do_package_qa[noexec] = "1"

INSANE_SKIP:${PN} = " already-stripped ldflags dev-elf"
EXCLUDE_FROM_SHLIBS = "1"
