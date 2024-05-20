import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetDataCategoryDetailComponent } from 'app/entities/dataset-data-category/dataset-data-category-detail.component';
import { DatasetDataCategory } from 'app/shared/model/dataset-data-category.model';

describe('Component Tests', () => {
  describe('DatasetDataCategory Management Detail Component', () => {
    let comp: DatasetDataCategoryDetailComponent;
    let fixture: ComponentFixture<DatasetDataCategoryDetailComponent>;
    const route = { data: of({ datasetDataCategory: new DatasetDataCategory(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetDataCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetDataCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetDataCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetDataCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
