# recovery-installer-ota-base

This repo is based on WireOS and is used to make the installer for installing unlock-prod and other recoveries. (Check branches for recovery installer builds)

## UNLOCKS
This current branch builds unlock-prod. But it expects some files to be in specific places:
- `ankidev-signed.img.gz` aboot in `anki/recovery-installer/ankidev-signed.img.gz`
    - The md5sum of the uncompressed aboot is `63647b910f00c9d599492c51901a2c06`
- `recovery.img.gz` boot image in `anki/recovery-installer/recovery.img.gz`
    - The md5sum of the uncompressed boot image is `1b30cd9ba7c364258cdc06fad2c076f3`
- `recoveryfs.img.gz` recovery system image in `anki/recovery-installer/recoveryfs.img.gz`

These can be grabbed from the latest unlock. The build IS DESIGNED TO FAIL if these hashes aren't met. 

## Build
Use the WireOS steps targetting prod and use the signing options.

### Where is my OTA?

`./_build/5.0.0.<increment>.ota`
