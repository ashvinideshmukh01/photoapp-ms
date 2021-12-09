package com.photoapp.userms.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.photoapp.userms.data.AlbumServiceClient.FallBackForAlbumService;
import com.photoapp.userms.ui.model.AlbummResponseModel;

@FeignClient(name="albums-ws",fallback = FallBackForAlbumService.class)
public interface AlbumServiceClient {
	@GetMapping("/users/{id}/albums")
	public List<AlbummResponseModel> getAlbums(@PathVariable String id);
		@Component
	class FallBackForAlbumService implements AlbumServiceClient{

		@Override
		public List<AlbummResponseModel> getAlbums(String id) {
			// TODO Auto-generated method stub
			return new ArrayList<AlbummResponseModel>();
		}
		
	}
}
