import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetDataCategoryComponent } from 'app/entities/dataset-data-category/dataset-data-category.component';
import { DatasetDataCategoryService } from 'app/entities/dataset-data-category/dataset-data-category.service';
import { DatasetDataCategory } from 'app/shared/model/dataset-data-category.model';

describe('Component Tests', () => {
  describe('DatasetDataCategory Management Component', () => {
    let comp: DatasetDataCategoryComponent;
    let fixture: ComponentFixture<DatasetDataCategoryComponent>;
    let service: DatasetDataCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetDataCategoryComponent],
        providers: [],
      })
        .overrideTemplate(DatasetDataCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DatasetDataCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DatasetDataCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DatasetDataCategory(123)],
            headers,
          }),
        ),
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.datasetDataCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
