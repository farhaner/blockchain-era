// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;


contract NasabahContract {

    struct Nasabah {
        bytes32 id;
        uint64 nik;
        string fullName;
        string[] contractFileCids;
        address owner;
        uint256 createTimeStamp;
        uint256 updateTimeStamp;
        bool exists;
    }

    struct NasabahInput {
        uint64 nik;
        string fullName;
        string[] contractFileCids;
    }

    struct NasabahUpdate {
        bytes32 newId;
        uint64 nik;
        string fullName;
    }

    mapping(bytes32 => Nasabah) private nasabahs;
    bytes32[] private nasabahIds;
    string[] private fileCids;

    event NasabahCreated(bytes32 indexed id, address indexed owner, uint64 nik, string fullName, uint256 createTimeStamp);
    event NasabahUpdated(bytes32 indexed oldId, bytes32 indexed newId,  string fullName, address indexed owner, uint256 updateTimeStamp);
    event NasabahDeleted(bytes32 indexed id, string fullName, address indexed owner, uint256 updateTimeStamp);
    event FileAdded(bytes32 indexed id, string cid, uint256 timestamp);

    function createNasabah(bytes32 id, NasabahInput calldata data) external returns (bytes32) {
        require(!nasabahs[id].exists, "Nasabah already exists");

        Nasabah storage n = nasabahs[id];
        n.id = id;
        n.nik = data.nik;
        n.fullName = data.fullName;
        n.owner = msg.sender;
        n.createTimeStamp = block.timestamp;
        n.updateTimeStamp = block.timestamp;
        n.exists = true;

        for (uint256 i = 0; i < data.contractFileCids.length; i++) {
            n.contractFileCids.push(data.contractFileCids[i]);
            fileCids.push(data.contractFileCids[i]);
        }

        nasabahIds.push(id);
        emit NasabahCreated(id, msg.sender, data.nik, data.fullName, block.timestamp);
        return id;
    }

    function getNasabah(bytes32 id) external view
    returns (
        bytes32,
        string memory,
        uint64,
        string[] memory,
        address,
        uint256,
        uint256
    ){
        require(nasabahs[id].exists, "Nasabah not found");

        Nasabah memory n = nasabahs[id];
        return (
            n.id,
            n.fullName,
            n.nik,
            n.contractFileCids,
            n.owner,
            n.createTimeStamp,
            n.updateTimeStamp
        );
    }

    function getAllNasabahIds() external view returns (bytes32[] memory) {
        return nasabahIds;
    }

    function getAllFileCids() external view returns (string[] memory) {
        return fileCids;
    }

    // 🔹 UPDATE Nasabah — jika NIK berubah maka ID juga ikut berubah
    function updateNasabah(bytes32 oldId, NasabahUpdate calldata data) external {
        require(nasabahs[oldId].exists, "Nasabah not found");
        require(nasabahs[oldId].owner == msg.sender, "Not the owner");

        Nasabah storage oldNasabah = nasabahs[oldId];

        bool idChanged = (data.nik != oldNasabah.nik);

        if (idChanged) {
            // 🔸 Tandai entri lama tidak aktif
            oldNasabah.exists = false;

            // 🔸 Buat entri baru
            Nasabah storage n = nasabahs[data.newId];
            n.id = data.newId;
            n.nik = data.nik;
            n.fullName = data.fullName;
            n.owner = msg.sender;
            n.createTimeStamp = oldNasabah.createTimeStamp;
            n.updateTimeStamp = block.timestamp;
            n.exists = true;

            // 🔸 Copy array CIDs manual
            for (uint i = 0; i < oldNasabah.contractFileCids.length; i++) {
                n.contractFileCids.push(oldNasabah.contractFileCids[i]);
            }

            nasabahIds.push(data.newId);
            emit NasabahUpdated(oldId, data.newId, data.fullName, msg.sender, block.timestamp);

        } else {
            // 🔸 Update normal tanpa ubah id
            oldNasabah.fullName = data.fullName;
            oldNasabah.updateTimeStamp = block.timestamp;

            emit NasabahUpdated(oldId, oldId, data.fullName, msg.sender, block.timestamp);
        }
    }

    function deleteNasabah(bytes32 id) external {
        require(nasabahs[id].exists, "Nasabah not found");
        require(nasabahs[id].owner == msg.sender, "Not the owner");

        Nasabah storage n = nasabahs[id];

        // Soft delete
        n.exists = false;

        // Clear personal data
        n.owner = address(0);
        n.updateTimeStamp = block.timestamp;

        // Clear array
        delete n.contractFileCids;

        emit NasabahDeleted(id, n.fullName, msg.sender, block.timestamp);
    }

    function addFileCid(bytes32 id, string calldata newCid) external {
        require(nasabahs[id].exists, "Nasabah not found");
        require(nasabahs[id].owner == msg.sender, "Not the owner");

        nasabahs[id].contractFileCids.push(newCid);
        nasabahs[id].updateTimeStamp = block.timestamp;
        emit FileAdded(id, newCid, block.timestamp);
    }

    function getFileCids(bytes32 id) external view returns (string[] memory) {
        require(nasabahs[id].exists, "Nasabah not found");
        return nasabahs[id].contractFileCids;
    }

    function getFileCount(bytes32 id) external view returns (uint256) {
        require(nasabahs[id].exists, "Nasabah not found");
        return nasabahs[id].contractFileCids.length;
    }
}
