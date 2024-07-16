import axios from "axios";

export const uploadImageToCloudinary = async (file) => {
	const formData = new FormData();
	formData.append("file", file);
	formData.append("upload_preset", "tiger_talks");

	try {
		const response = await axios.post(
			"https://api.cloudinary.com/v1_1/dp4j9a7ry/image/upload",
			formData
		);
		return response.data.secure_url;
	} catch (error) {
		console.error("Error uploading image:", error);
		throw error;
	}
};
